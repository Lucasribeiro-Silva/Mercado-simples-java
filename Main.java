import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static int quantidade_total_produtos = 0;
    static int max_produtos = 100;

    public static void main(String[] args) {
        menu();
    }
    public static void menu() {
        Scanner leia = new Scanner(System.in);
        ArrayList<String> nome_produtos = new ArrayList<>();
        ArrayList<Integer> codigo_produtos = new ArrayList<>();
        ArrayList<Integer> quantidade_produtos = new ArrayList<>();
        ArrayList<String> preco_produtos = new ArrayList<>();
        int op;

        while (true) {
            System.out.print("Digite qual opção deseja acessar:\n1 - Comprar\n2 - Visualizar Estoque\n3 - Cadastrar Estoque\n4 - Sair\nOpção: ");
            try {
                op = leia.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas número!!");
                leia.nextLine();
                continue;
            }

            if (op > 4 || op <= 0) {
                System.out.println("Digite um dos números disponíveis!");
                continue;
            }

            if (op == 1) {
                compra(leia, nome_produtos, codigo_produtos, quantidade_produtos, preco_produtos);
            } else if (op == 2) {
                estoque(nome_produtos, codigo_produtos, quantidade_produtos, preco_produtos, leia);
            } else if (op == 3) {
                cadastrar(leia, nome_produtos, codigo_produtos, quantidade_produtos, preco_produtos);
            } else {
                sair();
                break;
            }
        }
    }

    public static void compra(Scanner leia, ArrayList<String> nomeProdutos, ArrayList<Integer> codigoProdutos, ArrayList<Integer> quantidadeProdutos, ArrayList<String> precoProdutos) {

        System.out.println("Digite o código do produto:");
        int pesquisar_produto;
        int quantidade_compra;
        try {
            pesquisar_produto = leia.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Digite apenas número!!");
            leia.nextLine();
            return;
        }

        boolean existente = false;
        for (int i = 0; i < quantidade_total_produtos; i++) {
            if (pesquisar_produto == codigoProdutos.get(i)) {
                existente = true;
                if (quantidadeProdutos.get(i) == 0) {
                    System.out.println("Acabou o estoque deste produto!!");
                } else
                    System.out.println("Produto: " + nomeProdutos.get(i) + "\nPreço: R$" + precoProdutos.get(i) + "\nDigite a quantidade que deseja comprar:");
                try {
                    quantidade_compra = leia.nextInt();

                } catch (Exception e) {
                    System.out.println("Digite apenas números!!");
                    break;
                }

                int estoqueAtual = quantidadeProdutos.get(i);

                if (quantidade_compra > estoqueAtual) {
                    System.out.println("Quantidade insuficiente em nosso esotque para efetuar a compra");
                    i--;
                } else {

                    estoqueAtual -= quantidade_compra;
                    quantidadeProdutos.set(i, estoqueAtual);
                    double valor = Double.parseDouble(precoProdutos.get(i));

                    System.out.println("Total = R$" + (quantidade_compra * valor));
                    break;
                }
            }
        }

        if (!existente) {
            System.out.println("Esse código não existe no nosso sistema!!");
        }
    }
    public static void estoque(ArrayList<String> nomeProdutos, ArrayList<Integer> codigoProdutos, ArrayList<Integer> quantidadeProdutos, ArrayList<String> precoProdutos, Scanner leia){

        System.out.println("---------Estoque:---------");
        if (quantidade_total_produtos == 0) {
            System.out.println("Estoque está vazio!!");
        } else {

            for (int i = 0; i < quantidade_total_produtos; i++) {
                System.out.println("Produto: " + nomeProdutos.get(i) + "\nCodigo: " + codigoProdutos.get(i) + "\nQuantidade: " + quantidadeProdutos.get(i) + "\nPreço: R$" + precoProdutos.get(i) + "\n");
            }
            System.out.println("Deseja adicionar mais quantidade a um produto?\n1 - Sim\n2 - Não");
            int opcao;
            opcao = leia.nextInt();
            while (true) {
                if (opcao == 1) {
                    System.out.print("Digite o codigo do produto: ");
                    int cod;
                    try {
                        cod = leia.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("Digite apenas números!!");
                        leia.nextLine();
                        continue;
                    }
                    for (int i = 0; i < quantidade_total_produtos; i++){
                        if (cod == codigoProdutos.get(i)){
                            System.out.println("Produto encontrado: "+ nomeProdutos.get(i));
                            System.out.println("Quantas unidades deseja adicionar no estoque?");
                            try {
                                int quantidade = leia.nextInt();
                                int quantidadeAtual = quantidadeProdutos.get(i);
                                quantidadeAtual += quantidade;
                                quantidadeProdutos.set(i, quantidadeAtual);
                                break;
                            }catch (InputMismatchException e){
                                System.out.println("Digite apenas números!!");
                                leia.nextLine();
                                i--;
                            }
                        }else{
                            System.out.println("Código não encontrado!!");
                            leia.nextLine();
                            break;
                        }
                    }

                } else if (opcao == 2) {
                    break;
                }else{
                    System.out.println("Digite uma das opções disponiveis!!");
                }
                break;
            }
        }
    }
    public static void cadastrar(Scanner leia, ArrayList<String> nomeProdutos, ArrayList<Integer> codigoProdutos, ArrayList<Integer> quantidadeProdutos, ArrayList<String> precoProdutos) {
        if (quantidade_total_produtos >= max_produtos) {
            System.out.println("Estoque cheio, não é possível cadastrar mais produtos.");
            return;
        }

        leia.nextLine();
        String novo_nome;
        while (true) {
            System.out.println("Digite o nome do produto:");
            novo_nome = leia.nextLine();

            boolean nomeExiste = false;
            for (String nome : nomeProdutos) {
                if (novo_nome.equalsIgnoreCase(nome)) {
                    nomeExiste = true;
                    break;
                }
            }
            if (!nomeExiste) {
                nomeProdutos.add(novo_nome);
                break;
            } else {
                System.out.println("Nome já existente!! Digite outro nome.");
            }
        }

        int novo_codigo;
        while (true) {
            System.out.println("Digite o código do produto:");
            try {
                novo_codigo = leia.nextInt();
                if (novo_codigo < 0){
                    System.out.println("Digite um número maior que zero!!");
                    leia.nextLine();
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas número!!");
                leia.nextLine();
                continue;
            }

            boolean codigoExiste = false;
            for (int cod : codigoProdutos) {
                if (novo_codigo == cod) {
                    codigoExiste = true;
                    break;
                }
            }
            if (!codigoExiste) {
                codigoProdutos.add(novo_codigo);
                break;
            } else {
                System.out.println("Código já existente!! Digite outro:");
            }
        }

        while (true) {
            System.out.println("Digite a quantidade:");
            try {
                int quantidade = leia.nextInt();
                if (quantidade < 0){
                    System.out.println("Digite um número maior que zero!!");
                    leia.nextLine();
                    continue;
                }
                quantidadeProdutos.add(quantidade);
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas número!!");
                leia.nextLine();
                continue;
            }
            break;
        }

        while (true){
            System.out.println("Digite o preço do produto: exemplo (0.00)");
            String preco = leia.next().replace(",", ".");
            try {
                if (Double.parseDouble(preco) < 0){
                    System.out.println("Digite um número maior que zero!!");
                    leia.nextLine();
                    continue;
                }
                Double.parseDouble(preco);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas número!!");
                leia.nextLine();
                continue;
            }
            precoProdutos.add(preco);
            break;
        }

        quantidade_total_produtos++;
    }
    public static void sair() {
        System.out.println("Saindo...");
    }
}