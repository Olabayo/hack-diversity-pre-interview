package com.drift.interview.reporting;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Message;
import java.util.List;

public class ConversationMetricsCalculator {
  public ConversationMetricsCalculator() {}

  /**
   * Returns a ConversationResponseMetric object which can be used to power data visualizations on the front end.
   */
  ConversationResponseMetric calculateAverageResponseTime(Conversation conversation) {
    List<Message> messages = conversation.getMessages();

    // implement me!
    int responseCounter = 0;
    long totalResponseTimes = 0;
    long leadUserMessageTime = 0;
    boolean needResponse = false;
    double averageResponseTime = 0.0;

    for(Message msg: messages){
      if(!msg.isTeamMember() && !needResponse){
        needResponse = true;
        leadUserMessageTime = msg.getCreatedAt();
      }
      if(msg.isTeamMember() && needResponse){
        needResponse = false;
        totalResponseTimes += msg.getCreatedAt() - leadUserMessageTime;
        responseCounter++;
      }
    }

    if(!needResponse){
      averageResponseTime = (double)totalResponseTimes / responseCounter;
    }

    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(averageResponseTime)
        .build();
  }
}
