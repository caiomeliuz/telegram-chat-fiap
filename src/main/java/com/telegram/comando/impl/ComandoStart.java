package com.telegram.comando.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.telegram.comando.Comando;
import com.telegram.comando.ComandoEnum;
import com.telegram.modelo.ChatFiap;

public class ComandoStart implements Comando {

	@Override
	public SendResponse processar(TelegramBot bot, ChatFiap chat) throws Exception {
		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Bem vindo ao chatobot do grupo 2 do MBA em full stack development - design, engineering & deployment, ");
		mensagem.append("este projeto tem por finalidade a avalia��o final da disciplina de java, com o professor Rafael Tsuji Matsuyama.\n");
		mensagem.append("Este bot cont�m os seguintes comandos dispon�veis:");
		mensagem.append(ComandoEnum.exibirComandos());
		
		return bot.execute(new SendMessage(chat.getChatId(), mensagem.toString()));
	}

}
