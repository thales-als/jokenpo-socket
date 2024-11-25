import java.io.*;
import java.net.*;

public class Jogador {
    private static String enderecoServidor;
    private static final int PORTA = 3304;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Execute o comando no seguinte formato: java Jogador <ip-host>");
            return;
        } else {
            enderecoServidor = args[0];
        }

        try (Socket socket = new Socket(enderecoServidor, PORTA)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            String mensagem;
            while ((mensagem = in.readLine()) != null) {
                System.out.println(mensagem);

                if (mensagem.equals("Faça sua jogada: (1 - Papel, 2 - Pedra, 3 - Tesoura)")) {
                    int jogada = Integer.parseInt(console.readLine());
                    out.println(jogada);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
