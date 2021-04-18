/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mycompany.kupepexamtaker;
import com.mycompany.classes.*;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java. net. InetAddress;

import java.util.ArrayList;
import java.util.List;

import static io.netty.channel.ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

    private ClientController cController;

    /**
     * Creates a client-side handler.
     */
    public ObjectEchoClientHandler(ClientController cController) {
        this.cController = cController;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws UnknownHostException {
        // Send the first message if this handler is a client-side handler.
        cController.setCtx(ctx);
        InetAddress inetAddress = InetAddress. getLocalHost();
        ClientArrived firstMessage = new ClientArrived(inetAddress. getHostAddress(),System.getProperty("user.name"),"10",System.getProperty("os.name"));
        ChannelFuture future = ctx.writeAndFlush(firstMessage);
        future.addListener(FIRE_EXCEPTION_ON_FAILURE); // Let object serialisation exceptions propagate.
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ChatMessageToStudent) {
            ChatMessageToStudent chatMessageToStudent = (ChatMessageToStudent)msg;
            cController.receiveChatMessageToStudent(chatMessageToStudent);
        } 
        else if (msg instanceof ChatMessagePublic) {
            ChatMessagePublic chatMessagePublic = (ChatMessagePublic)msg;
            cController.receiveChatMessagePublic(chatMessagePublic);
        } 
        else if (msg instanceof TimeRemaining) {
            TimeRemaining timeRemaining = (TimeRemaining)msg;
            cController.setTimeRemaining(timeRemaining.getTimeRemaing());
        } else if (msg instanceof ExamSetting) {
            ExamSetting examSetting = (ExamSetting)msg;
            cController.setExamSetting(examSetting);
        } else if (msg instanceof FileMessage) {
            FileMessage fm = (FileMessage)msg;
            cController.setExamFile(fm);

        } else if (msg instanceof ExamStarted) {
            ExamStarted examStarted = (ExamStarted)msg;
            cController.informExamStarted(examStarted);

        }
         else if (msg instanceof ExamExtended) {
            ExamExtended examExtended = (ExamExtended)msg;
            cController.informExamExtended(examExtended);

        }
         else if (msg instanceof ExamStopped) {
            ExamStopped examStopped = (ExamStopped)msg;
            cController.informExamStopped(examStopped);

        }
        else if (msg instanceof HelpComing) {
            HelpComing helpComing = (HelpComing)msg;
            cController.informHelpComing(helpComing);

        }
        else if (msg instanceof PMEnabled) {
            PMEnabled pMEnabled = (PMEnabled)msg;
            cController.setPMEnabled(pMEnabled);

        }
        else if (msg instanceof OpenDialog) {
            OpenDialog openDialog = (OpenDialog)msg;
            cController.setOpenDiaolog(openDialog.isOn());

        }



    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
