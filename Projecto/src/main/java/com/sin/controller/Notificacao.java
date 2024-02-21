/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sin.controller;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
//import org.controlsfx.control.Notifications;

/**
 *
 * @author Stelio_AM
 */
public class Notificacao {
       public void notificacaoSucesso(String texto) {
        Image img = new Image("/img/confirm.png");
        Notifications notification = Notifications.create()
                .title("Sucesso ao realizar processo")
                .text(texto)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_RIGHT)
                .onAction(event1 -> {
                    // Aqui você pode adicionar o código que deseja executar quando a notificação for clicada
                    System.out.println("Notificação clicada!");
                });
        notification.darkStyle();
        notification.show(); 
    }
    public void notificacaoFalha(String texto) {
        Image img = new Image("/img/close.png");
        Notifications notification = Notifications.create()
                .title("Falha ao realizar processo")
                .text(texto)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_RIGHT)
                .onAction(event1 -> {
                    // Aqui você pode adicionar o código que deseja executar quando a notificação for clicada
                    System.out.println("Notificação clicada!");
                });
        notification.darkStyle();
        notification.show(); 
    }
    public void notificacaoWarning(String texto) {
        Image img = new Image("/img/error.png");
        Notifications notification = Notifications.create()
                .title("Warnng Notification")
                .text(texto)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_RIGHT)
                .onAction(event1 -> {
                    // Aqui você pode adicionar o código que deseja executar quando a notificação for clicada
                    System.out.println("Notificação clicada!");
                });
        notification.darkStyle();
        notification.show(); 
    }
}
