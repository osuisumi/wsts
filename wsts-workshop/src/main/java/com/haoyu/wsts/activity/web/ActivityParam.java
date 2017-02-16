package com.haoyu.wsts.activity.web;

import java.io.Serializable;

import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.courseware.entity.Courseware;
import com.haoyu.aip.debate.entity.Debate;
import com.haoyu.aip.discussion.entity.Discussion;
import com.haoyu.aip.lessonplan.entity.LessonPlan;
import com.haoyu.aip.qti.entity.Test;
import com.haoyu.aip.survey.entity.Survey;
import com.haoyu.aip.video.entity.Video;

public class ActivityParam implements Serializable {

	private static final long serialVersionUID = 6615033007480337515L;

	private Activity activity;

	private Discussion discussion;

	private Video video;

	private Test test;

	private Survey survey;

	private LessonPlan lessonPlan;

	private Debate debate;

	private Courseware lcec;

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public LessonPlan getLessonPlan() {
		return lessonPlan;
	}

	public void setLessonPlan(LessonPlan lessonPlan) {
		this.lessonPlan = lessonPlan;
	}

	public Debate getDebate() {
		return debate;
	}

	public void setDebate(Debate debate) {
		this.debate = debate;
	}

	public Courseware getLcec() {
		return lcec;
	}

	public void setLcec(Courseware lcec) {
		this.lcec = lcec;
	}

}
