package Arvore;

import java.util.Random;

public class Arvore<TIPO extends Comparable<TIPO>> {

    private Elemento<TIPO> raiz;

    public Arvore() {
        this.raiz = null;
    }

    public void adicionar(TIPO valor) {
        Elemento<TIPO> novoElemento = new Elemento<TIPO>(valor);
        if (raiz == null) {
            this.raiz = novoElemento;
        } else {
            Elemento<TIPO> atual = this.raiz;
            while (true) {
                int comparacao = novoElemento.getValor().compareTo(atual.getValor());

                if (comparacao < 0) { // novoElemento é menor que atual
                    if (atual.getEsquerda() != null) {
                        atual = atual.getEsquerda();
                    } else {
                        atual.setEsquerda(novoElemento);
                        break;
                    }
                } else if (comparacao > 0) { // novoElemento é maior que atual
                    if (atual.getDireita() != null) {
                        atual = atual.getDireita();
                    } else {
                        atual.setDireita(novoElemento);
                        break;
                    }
                } else { // Valores iguais (duplicata)
                    // Para uma BST clássica, duplicatas geralmente não são adicionadas ou são tratadas de forma específica.
                    break;
                }
            }
        }
    }

    public Elemento<TIPO> getRaiz() {
        return raiz;
    }

    public void emOrdem(Elemento<TIPO> atual) {
        if (atual != null) {
            emOrdem(atual.getEsquerda());
            System.out.print(atual.getValor() + " ");
            emOrdem(atual.getDireita());
        }
    }

    public void emOrdemReversa(Elemento<TIPO> atual) {
        if (atual != null) {
            emOrdemReversa(atual.getDireita());
            System.out.println(atual.getValor() + " ");
            emOrdemReversa(atual.getEsquerda());
        }
    }

    public void preOrdem(Elemento<TIPO> atual) {
        if (atual != null) {
            System.out.print(atual.getValor() + " ");
            preOrdem(atual.getEsquerda());
            preOrdem(atual.getDireita());
        }
    }

    public void posOrdem(Elemento<TIPO> atual) {
        if (atual != null) {
            posOrdem(atual.getEsquerda());
            posOrdem(atual.getDireita());
            System.out.print(atual.getValor() + " ");
        }
    }

    // --- Início das Correções no método 'remover' ---
    public boolean remover(TIPO valor) {
        // Inicializa paiAtual como null, pois pode ser a raiz que está sendo removida
        Elemento<TIPO> atual = this.raiz;
        Elemento<TIPO> paiAtual = null;

        // 1. Busca o elemento a ser removido e seu pai
        while (atual != null && !atual.getValor().equals(valor)) {
            paiAtual = atual; // Atualiza o paiAtual antes de mover para o próximo nó
            if (valor.compareTo(atual.getValor()) < 0) { // valor é menor, vai para a esquerda
                atual = atual.getEsquerda();
            } else { // valor é maior, vai para a direita
                atual = atual.getDireita();
            }
        }

        // Se 'atual' é null, o elemento não foi encontrado
        if (atual == null) {
            return false;
        }

        // Caso 1: Nó a ser removido não tem filhos ou tem apenas um filho
        // (Lógica mais compacta para lidar com 0 ou 1 filho)
        if (atual.getEsquerda() == null || atual.getDireita() == null) {
            Elemento<TIPO> filho = null; // Filho que substituirá o nó 'atual'

            if (atual.getEsquerda() != null) {
                filho = atual.getEsquerda(); // Se tem filho à esquerda, ele é o substituto
            } else {
                filho = atual.getDireita(); // Se tem filho à direita (ou nenhum), ele é o substituto
            }

            // Conecta o filho (ou null, se não tiver filhos) ao pai do nó 'atual'
            if (paiAtual == null) { // 'atual' é a raiz
                this.raiz = filho;
            } else if (atual.equals(paiAtual.getEsquerda())) { // 'atual' é filho esquerdo do pai
                paiAtual.setEsquerda(filho);
            } else { // 'atual' é filho direito do pai
                paiAtual.setDireita(filho);
            }
        } // Caso 2: Nó a ser removido tem dois filhos
        else {
            // Encontra o sucessor (menor valor na subárvore direita)
            Elemento<TIPO> substituto = atual.getDireita();
            Elemento<TIPO> paiSubstituto = atual; // O pai do substituto, que inicialmente é o nó atual

            while (substituto.getEsquerda() != null) {
                paiSubstituto = substituto;
                substituto = substituto.getEsquerda();
            }

            // O valor do nó 'atual' é substituído pelo valor do sucessor
            atual.setValor(substituto.getValor());

            // Agora, o problema é remover o 'substituto' da sua posição original.
            // O 'substituto' não terá filho à esquerda, mas pode ter filho à direita.
            // Conecta o filho direito do substituto (ou null) ao pai do substituto.
            if (substituto.equals(paiSubstituto.getEsquerda())) { // substituto é filho esquerdo do paiSubstituto
                paiSubstituto.setEsquerda(substituto.getDireita());
            } else { // substituto é filho direito do paiSubstituto (isso acontece se o sucessor for o filho direito direto do nó a ser removido)
                paiSubstituto.setDireita(substituto.getDireita());
            }
        }
        return true; // Elemento removido com sucesso
    }

    public boolean buscar(TIPO valor) {
        return buscarNaArvore(this.raiz, valor);
    }

    private boolean buscarNaArvore(Elemento<TIPO> atual, TIPO valor) {
        if (atual == null) {
            return false;
        }
        int comparacao = valor.compareTo(atual.getValor());

        if (comparacao == 0) {
            return true;
        } else if (comparacao < 0) {
            return buscarNaArvore(atual.getEsquerda(), valor);
        } else {
            return buscarNaArvore(atual.getDireita(), valor);
        }
    }

    public int getAltura() {
        return calcularAltura(this.raiz);
    }

    private int calcularAltura(Elemento<TIPO> atual) {
        if (atual == null) {
            return -1;
        }
        int alturaEsquerda = calcularAltura(atual.getEsquerda());
        int alturaDireita = calcularAltura(atual.getEsquerda());

        return 1 + Math.max(alturaEsquerda, alturaDireita);
    }

    public TIPO getNoSeguinte(TIPO valor) {
        Elemento<TIPO> noEncontrado = buscarElemento(this.raiz, valor);

        if (noEncontrado == null) {
            return null;
        }

        // AGORA CHAME O MÉTODO COM APENAS UM ARGUMENTO:
        Elemento<TIPO> sucessor = encontrarSucessor(noEncontrado); // <<< CORRIGIDO AQUI!

        // Sua lógica if-else para retornar o valor ou null
        if (sucessor != null) {
            return sucessor.getValor();
        } else {
            return null;
        }
    }

    private Elemento<TIPO> buscarElemento(Elemento<TIPO> atual, TIPO valor) {
        while (atual != null) {
            int comparacao = valor.compareTo(atual.getValor());
            if (comparacao == 0) {
                return atual;
            } else if (comparacao < 0) {
                atual = atual.getEsquerda();
            } else {
                atual = atual.getDireita();
            }
        }
        return null;
    }

    private Elemento<TIPO> encontrarSucessor(Elemento<TIPO> no) {
        if (no.getDireita() != null) {
            Elemento<TIPO> temp = no.getDireita();
            while (temp.getEsquerda() != null) {
                temp = temp.getEsquerda();
            }
            return temp;
        } else {
            Elemento<TIPO> sucessor = null;
            Elemento<TIPO> atual = this.raiz;

            while (atual != null) {
                if (no.getValor().compareTo(atual.getValor()) < 0) {
                    sucessor = atual;
                    atual = atual.getEsquerda();
                } else if (no.getValor().compareTo(atual.getValor()) > 0) {
                    atual = atual.getDireita();
                } else {
                    break;
                }
            }
            return sucessor;
        }
    }
    
    public void adicionarAutomatico(int quantidade){
        Random rand = new Random();
        
        for(int i = 0; i < quantidade; i++){
            if(this.getRaiz() == null || this.getRaiz().getValor() instanceof Integer){
                int valorGerado = rand.nextInt(100);
                
                this.adicionar((TIPO) Integer.valueOf(valorGerado));
                System.out.println(valorGerado + " ");
            }else{
                System.out.println("A geração aleatória aceita apenas números");
                break;
            }
        }
        System.out.println("\nConcluido");
    }

}
