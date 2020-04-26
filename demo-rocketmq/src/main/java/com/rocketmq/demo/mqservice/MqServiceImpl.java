package com.bzl.robot.mqservice;


import com.alibaba.fastjson.JSONObject;
import com.bzl.robot.contant.MessageTopicConstant;
import com.bzl.robot.service.MqService;
import com.bzl.robot.vo.DebSummaryInfo;
import com.bzl.robot.vo.ResultData;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

@RestController
public class MqServiceImpl implements MqService {


    @Autowired
    private DefaultMQProducer defaultMQProducer;


   // @Autowired
    private DefaultMQPushConsumer defaultMQPushConsumer;

    private static final Logger log = LoggerFactory.getLogger(MqServiceImpl.class);

    private static  final String head = "GET /%s HTTP/1.1\r\nContent-Type: application/json; charset=UTF-8\r\nHost: %s:9999\r\nUser-Agent: robot web server\r\n\r\n";

    private static  final String postHead = "GET /%s HTTP/1.1\r\nContent-Type: application/json; charset=UTF-8\r\nHost: 192.168.175.131:9999\r\nUser-Agent: robot web server\r\n\r\n";


    /**
     *  下发任务：包括地图
     * @param taskVO
     * @return
     */
    @Override
    public ResultData dispatchTask(@RequestBody JSONObject taskVO) {
        String deviceCode = taskVO.getString ("DeviceCode");
        String MsgID = UUID.randomUUID ().toString ();
        taskVO.put ("MsgID",MsgID);
        String topic = String.format (MessageTopicConstant.C0NNECT_TASK,deviceCode);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (taskVO).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
           log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发地图发送成功================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }


    /**
     *  下发行为控制
     * @param control
     * @return
     */
    @Override
    public ResultData dispatchControl(@RequestBody JSONObject control) {
        String deviceCode = control.getString ("DeviceCode");
        String topic = String.format (MessageTopicConstant.C0NNECT_CONTROL,deviceCode);
        String MsgID = UUID.randomUUID ().toString ();
        control.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (control).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发行为控制发送成功==============="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }

    /**
     *  下发参数配置
     * @param conf
     * @return
     */
    @Override
    public ResultData dispatchConf(@RequestBody JSONObject conf) {
        String deviceCode = conf.getString ("DeviceCode");
        String topic = String.format (MessageTopicConstant.C0NNECT_CONF,deviceCode);
        String MsgID = UUID.randomUUID ().toString ();
        conf.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (conf).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发参数配置发送成功=================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }


    /**
     *  下发开关机
     * @param switchInfo
     * @return
     */
    @Override
    public ResultData dispatchSwitch(@RequestBody JSONObject switchInfo) {
        String deviceCode = switchInfo.getString ("DeviceCode");
        String topic = String.format (MessageTopicConstant.C0NNECT_SWITH,deviceCode);
        String MsgID = UUID.randomUUID ().toString ();
        switchInfo.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (switchInfo).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发开关机发送成功================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }


    /**
     *
     * @param request　下发设备操作指令
     * @return
     */
    @Override
    public ResultData dispatchRequest(@RequestBody JSONObject request) {
        String deviceCode = request.getString ("DeviceCode");
        String topic = String.format (MessageTopicConstant.CONNECT_REQUEST,deviceCode);
        String MsgID = UUID.randomUUID ().toString ();
        request.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (request).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发设备操作指令发送成功================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }


    /**
     *
     * @param notify　发送电梯相关通知指令
     * @return
     */
    @Override
    public ResultData dispatchElevatorNotify(@RequestBody JSONObject notify) {
        String deviceCode = notify.getString ("DeviceCode");
        String topic = String.format (MessageTopicConstant.CONNECT_NOTIFY,deviceCode);
        String MsgID = UUID.randomUUID ().toString ();
        notify.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (notify).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发电梯相关通知指令发送成功================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }

    /**
     *
     * @param verticalmove　　控制电梯运行到目标楼层
     * @return
     */
    @Override
    public ResultData dispatchElevatorVerticalmove(@RequestBody JSONObject verticalmove) {
        String deviceCode = verticalmove.getString ("DeviceCode");
        String topic = String.format (MessageTopicConstant.CONNECT_VERTICALMOVE,deviceCode);
        String MsgID = UUID.randomUUID ().toString ();
        verticalmove.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (verticalmove).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发控制电梯运行到目标楼层发送成功================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }


    /**
     *
     * @param command　　控制电梯急停
     * @return
     */
    @Override
    public ResultData dispatchElevatorCommand(@RequestBody JSONObject command) {
        String deviceCode = command.getString ("DeviceCode");
       // String topic = String.format (MessageTopicConstant.CONNECT_COMMAND,deviceCode);
        String topic = "connect.CRNZTFMM1908001.call_webserver_service";
        String MsgID = UUID.randomUUID ().toString ();
        command.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (command).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============下发控制电梯急停发送成功================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }



    /**
     *
     * @param deviceAck　　　系统对设备上报信息响应
     * @return
     */
    @Override
    public ResultData dispatchDeviceAck(@RequestBody JSONObject deviceAck) {
        String deviceCode = deviceAck.getString ("DeviceCode");
        String topic = String.format (MessageTopicConstant.CONNECT_DEVICE_ACK,deviceCode);
        String MsgID = UUID.randomUUID ().toString ();
        deviceAck.put ("MsgID",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString (deviceAck).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info("============系统对设备上报信息响应发送成功================="/*, JSONObject.toJSONString (taskVO)*/);
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }

    /**
     *  下发deb升级包
     * @param
     * @return
     */
    @Override
    public ResultData dispatchDebPackage(@RequestBody DebSummaryInfo debSummaryInfo) {
        JSONObject send = new JSONObject();
        for (String s:debSummaryInfo.getDeviceCode()){
            String topic = String.format (MessageTopicConstant.CONNECT_DISPATCH_OTA,s);
            send.put("name",debSummaryInfo.getPackage());
            send.put("version",debSummaryInfo.getVersion());
            Message sendMsg = new Message(topic,send.toJSONString().getBytes ());
            try {
                defaultMQProducer.send(sendMsg);
            } catch (Exception e) {
                log.info ("============= 发送消息异常=================={}",e);
            }
        }
        ResultData resultData = new ResultData();
        resultData.setMsg ("发送成功");
        return  resultData;
    }

    @Override
    public byte[] getArgSetIndex(@RequestParam("deviceCode") String deviceCode,@RequestParam("path") String path,@RequestParam("ip") String ip) {

        String topic = String.format (MessageTopicConstant.CONNECT_GET_INDEX,deviceCode);
        // 发送
        String content = String.format (head,path,ip);
        JSONObject obj = new JSONObject ();
        String MsgID = UUID.randomUUID().toString().replaceAll("-", "");
        obj.put ("content",content);
        obj.put ("msgId",MsgID);
        Message sendMsg = new Message(topic,JSONObject.toJSONString(obj).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info (JSONObject.toJSONString(obj));
        log.info ("==============发送请求成功============={}",deviceCode+"====="+content);

        // 接收
        String receiveTopic = String.format (MessageTopicConstant.DEVICE_GET_INDEX,MsgID);
//        String text = consumerService.receive (receiveDestination);
//        log.info ("==============getIndexHTML============={}",text);
//        log.info("============获取参数设置indexhtml================="/*, JSONObject.toJSONString (taskVO)*/);
//        ResultData resultData = new ResultData();
//        resultData.setMsg ("发送成功");
//        resultData.setData (text);
        log.info ("========mq开始获取静态资源=========="+path);
        final byte[][] text = new byte[1][1];
        try {
            defaultMQPushConsumer.subscribe (receiveTopic,"*");
            defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently () {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    //System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    text[0] = msgs.get (0).getBody ();
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            defaultMQPushConsumer.start ();
        } catch (MQClientException e) {
          log.info ("===========监听异常============={}",e);
        }

        if(text!=null){
            log.info ("========mq获取静态资源=========="+path+"成功"+text.length);
            return  text[0];
        }else{
            return  "获取资源为空或超时".getBytes (Charset.defaultCharset ());
        }
    }



    @Override
    public byte[] postArgSetIndex(@RequestParam("deviceCode") String deviceCode,  @RequestParam("content") String content) throws IOException {
        log.info ("========mqPOST请求内容=========="+content);
        JSONObject obj = new JSONObject ();
        String MsgID = UUID.randomUUID().toString().replaceAll("-", "");
        obj.put ("content",content);
        obj.put ("msgId",MsgID);
        String topic = String.format (MessageTopicConstant.CONNECT_GET_INDEX,deviceCode);
        // 发送
        Message sendMsg = new Message(topic,JSONObject.toJSONString(obj).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info (JSONObject.toJSONString(obj));
        log.info ("==============发送请求成功============={}",deviceCode+"====="+content);
        // 接收
        String receiveTopic = String.format (MessageTopicConstant.DEVICE_GET_INDEX,MsgID);
        final byte[][] text = new byte[1][1];
        try {
            defaultMQPushConsumer.subscribe (receiveTopic,"*");
            defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently () {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    //System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    text[0] = msgs.get (0).getBody ();
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            defaultMQPushConsumer.start ();
        } catch (MQClientException e) {
            log.info ("===========监听异常============={}",e);
        }
        if(text!=null){
            log.info ("========mq获取静态资源==========成功"+text.length);
            return   text[0];
        }else{
            return  "获取资源为空或超时".getBytes (Charset.defaultCharset ());
        }
    }

    @Override
    public byte[] postUpload(@RequestParam("deviceCode") String deviceCode,  @RequestParam("content") byte[] content)  {
        log.info ("========mqPOST请求内容=========="+content);
        JSONObject obj = new JSONObject ();
        String MsgID = UUID.randomUUID().toString().replaceAll("-", "");
        obj.put ("content",content);
        obj.put ("msgId",MsgID);
        String topic = String.format (MessageTopicConstant.CONNECT_GET_INDEX,deviceCode);
        // 发送
        Message sendMsg = new Message(topic,JSONObject.toJSONString(obj).getBytes ());
        try {
            defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.info ("============= 发送消息异常=================={}",e);
        }
        log.info (JSONObject.toJSONString(obj));
        log.info ("==============发送请求成功============={}",deviceCode+"====="+content);
        // 接收
        String receiveTopic = String.format (MessageTopicConstant.DEVICE_GET_INDEX,MsgID);
        final byte[][] text = new byte[1][1];
        try {
            defaultMQPushConsumer.subscribe (receiveTopic,"*");
            defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently () {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    //System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    text[0] = msgs.get (0).getBody ();
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            defaultMQPushConsumer.start ();
        } catch (MQClientException e) {
            log.info ("===========监听异常============={}",e);
        }
        if(text!=null){
            log.info ("========mq获取静态资源==========成功"+text.length);
            return  text[0] ;
        }else{
            return  "获取资源为空或超时".getBytes (Charset.defaultCharset ());
        }
    }

}
