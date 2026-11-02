/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arvore;

/**
 *
 * @author Layer
 */
import java.util.Scanner;
import java.util.InputMismatchException; // Para tratar erros de entrada
public class Aplicação {
        public static void main(String[] args) {
        Arvore<Integer> arvore = new Arvore<>();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Operações da Árvore ---");
            System.out.println("1.  Inserir um valor");
            System.out.println("2.  Inserir valores automaticamente");
            System.out.println("3.  Buscar um valor");
            System.out.println("4.  Remover um valor");
            System.out.println("5.  Varredura E-R-D");
            System.out.println("6.  Varredura D-R-E");
            System.out.println("0.  Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                switch (opcao) {
                    case 1:
                        System.out.print("Digite o valor a ser inserido: ");
                        int valor = scanner.nextInt();
                        arvore.adicionar(valor);
                        System.out.println("Valor " + valor + " inserido.");
                        break;
                    case 2:
                        System.out.print("Quantos valores deseja inserir automaticamente? ");
                        int quantidade = scanner.nextInt();
                        arvore.adicionarAutomatico(quantidade);
                        System.out.println(quantidade + " valores adicionados automaticamente.");
                        break;
                    case 3:
                        System.out.print("Digite o valor a ser buscado: ");
                        int busca = scanner.nextInt();
                        if (arvore.buscar(busca)) {
                            System.out.println("Valor " + busca + " encontrado na arvore.");
                        } else {
                            System.out.println("Valor " + busca + " NAO encontrado na arvore.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o valor a ser removido: ");
                        int n = scanner.nextInt();
                        if (arvore.remover(n)) {
                            System.out.println("Valor " + n + " removido com sucesso.");
                        } else {
                            System.out.println("Valor " + n + " não encontrado para remoção.");
                        }
                        break;
                    case 5:
                        System.out.print("Percurso Em Ordem (E-R-D): ");
                        arvore.emOrdem(arvore.getRaiz());
                        System.out.println();
                        break;
                    case 6:
                        System.out.print("Percurso Em Ordem Reversa (D-R-E): ");
                        arvore.emOrdemReversa(arvore.getRaiz());
                        System.out.println();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // Limpa o buffer do scanner para evitar loop infinito
                opcao = -1; // Garante que o loop continue
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
                e.printStackTrace();
                opcao = -1;
            }

        } while (opcao != 0);

        scanner.close();
    }
}
