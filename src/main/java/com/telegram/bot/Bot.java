package com.telegram.bot;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.telegram.comando.Comando;
import com.telegram.comando.ComandoFactory;
import com.telegram.modelo.ChatFiap;

public class Bot {

	private TelegramBot bot;

	private int lastUpdateId = 0;

	public Bot(String token) {
		System.out.println("Iniciando bot grupo 2 java...");

		if (token == null || token.isEmpty()) {
			throw new NullPointerException("Token do telegram precisa ser preenchido /resources/config.properties");
		}

		this.bot = new TelegramBot(token);
	}

	public void executar() {
		System.out.println("Bot grupo 2 java iniciado...");

		// Loop infinito pode ser alterado por algum timer de intervalo curto.
		while (true) {
			// Executa comando no Telegram para obter as mensagens pendentes a partir de um
			// off-set (limite inicial)
			List<Update> updates = this.bot.execute(new GetUpdates().limit(100).offset(this.lastUpdateId)).updates();

			if (updates.isEmpty()) {
				continue;
			}

			// Analise de cada acao da mensagem.
			for (Update update : updates) {

				setLastUpdateId(update.updateId());

				// Recebendo mensagem
				String mensagem = update.message().text();
				System.out.println("Recebendo mensagem: " + mensagem);

				// Envio de "Escrevendo" antes de enviar a resposta.
				this.getUpdates(update.message().chat().id());

				try {
					enviarMensagem(new ChatFiap(update));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void enviarMensagem(ChatFiap chat) throws Exception {
		Comando comando = ComandoFactory.getComando(chat.getMessage());
		SendResponse sendResponse = comando.processar(this.bot, chat);

		// Verificacao de mensagem enviada com sucesso.
		System.out.println("Mensagem Enviada? " + sendResponse.isOk());
	}

	private void getUpdates(Long chatId) {
		BaseResponse baseResponse = this.bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));

		// Verificacao de acao de chat foi enviada com sucesso.
		System.out.println("Resposta de Chat Action Enviada? " + baseResponse.isOk());
	}

	private void setLastUpdateId(Integer updateId) {
		this.lastUpdateId = updateId + 1;
	}
}
