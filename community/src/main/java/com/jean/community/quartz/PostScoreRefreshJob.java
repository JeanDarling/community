package com.jean.community.quartz;

import com.jean.community.entity.DiscussPost;
import com.jean.community.service.DiscusspostService;
import com.jean.community.service.ElasticsearchService;
import com.jean.community.service.LikeService;
import com.jean.community.util.CommunityConstant;
import com.jean.community.util.RedisKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by The High Priestess
 *
 * @description quartz 帖子分数刷新
 */
public class PostScoreRefreshJob implements Job, CommunityConstant {

    private static final Logger log = LoggerFactory.getLogger(PostScoreRefreshJob.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscusspostService discusspostService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    // 时间纪元
    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-09-24 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("初始化纪元失败！", e);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);

        if (operations.size() == 0) {
            log.info("[任务取消]，没有需要刷新的帖子");
            return;
        }

        log.info("[任务开始]，正在刷新帖子分数: " + operations.size());
        while (operations.size() > 0) {
            this.refresh((Integer) operations.pop());
        }
        log.info("[任务结束]，帖子分数刷新完毕！ ");
    }

    private void refresh(int postId) {
        DiscussPost post = discusspostService.findDiscussPostByID(postId);

        if (post == null) {
            log.error("该帖子不在：id = " + postId);
            return;
        }

        // 是否精化
        boolean wonderful = post.getStatus() == 1;
        // 评论数量
        int commentCount = post.getCommentCount();
        // 点赞数量
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, postId);
        // 计算权重
        double w = (wonderful ? 75 : 0) + commentCount + likeCount * 2;
        // 分数 = 帖子权重+距离天数；
        double score = Math.log10(Math.max(w, 1))
                + (post.getCreateTime().getTime() - epoch.getTime()) / (1000*3600*24) ;
        // 更新帖子分数
        discusspostService.updateScore(postId, score);
        // 同步搜索数据
        post.setScore(score);
        elasticsearchService.saveDiscussPost(post);
    }
}
