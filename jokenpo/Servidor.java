import java.io.*;
import java.net.*;

public class Servidor {
    private static final int PORTA = 3304;
    private static Socket jogador1;
    private static Socket jogador2;
    private static boolean jogoEmAndamento = true;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado. Aguardando jogadores...");
            
            jogador1 = serverSocket.accept();
            System.out.println("Jogador 1 conectado.");
            PrintWriter out1 = new PrintWriter(jogador1.getOutputStream(), true);
            out1.println("Aguardando Jogador 2...");
            
            jogador2 = serverSocket.accept();
            System.out.println("Jogador 2 conectado.");
            PrintWriter out2 = new PrintWriter(jogador2.getOutputStream(), true);
            out2.println("Jogo iniciado!");
            
            jogar(jogador1, jogador2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void jogar(Socket jogador1, Socket jogador2) {
        try {
            BufferedReader in1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
            PrintWriter out1 = new PrintWriter(jogador1.getOutputStream(), true);
            PrintWriter out2 = new PrintWriter(jogador2.getOutputStream(), true);
            
            out1.println("Você é o jogador 1.");
            
            out2.println("Você é o jogador 2.");

            while (jogoEmAndamento) {
                out1.println("Faça sua jogada: (1 - Papel, 2 - Pedra, 3 - Tesoura)");
                out2.println("Faça sua jogada: (1 - Papel, 2 - Pedra, 3 - Tesoura)");
                
                int jogada1 = Integer.parseInt(in1.readLine());
                int jogada2 = Integer.parseInt(in2.readLine());
                
                String resultado = verificarResultado(jogada1, jogada2);
                
                out1.println("O jogador 1 escolheu: " + jogada1);
                out1.println("O jogador 2 escolheu: " + jogada2);
                out1.println("Resultado: " + resultado);
                
                out2.println("O jogador 1 escolheu: " + jogada1);
                out2.println("O jogador 2 escolheu: " + jogada2);
                out2.println("Resultado: " + resultado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fechaConexoesEEncerraJogo();
        }
    }

    private static String verificarResultado(int jogada1, int jogada2) {
        if (jogada1 == jogada2) {
            return "Empate!";
        } else if ((jogada1 == 1 && jogada2 == 2) || 
                   (jogada1 == 2 && jogada2 == 3) || 
                   (jogada1 == 3 && jogada2 == 1)) {
            return "Jogador 1 ganhou!";
        } else {
            return "Jogador 2 ganhou!";
        }
    }

    private static void fechaConexoesEEncerraJogo() {
        try {
            if (jogador1 != null) jogador1.close();
            if (jogador2 != null) jogador2.close();
            jogoEmAndamento = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}