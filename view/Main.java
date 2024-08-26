package main;

import dao.PessoaDao;
import model.Pessoa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PessoaDao pessoaDao = new PessoaDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Salvar uma pessoa");
            System.out.println("2. Listar todas as pessoas");
            System.out.println("3. Deletar uma pessoa pelo e-mail");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    salvarPessoa(pessoaDao, scanner);
                    break;
                case 2:
                    listarPessoas(pessoaDao);
                    break;
                case 3:
                    deletarPessoa(pessoaDao, scanner);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void salvarPessoa(PessoaDao pessoaDao, Scanner scanner) {
        System.out.print("Digite o nome da pessoa: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o e-mail da pessoa: ");
        String email = scanner.nextLine();
        Pessoa pessoa = new Pessoa(nome, email);

        if (pessoaDao.salvar(pessoa)) {
            System.out.println("Pessoa salva com sucesso.");
        } else {
            System.out.println("Já existe uma pessoa com este e-mail.");
        }
    }

    private static void listarPessoas(PessoaDao pessoaDao) {
        System.out.println("\nLista de pessoas:");
        for (Pessoa p : pessoaDao.getPessoas()) {
            System.out.println("Nome: " + p.getNome() + ", E-mail: " + p.getEmail());
        }
    }

    private static void deletarPessoa(PessoaDao pessoaDao, Scanner scanner) {
        System.out.print("Digite o e-mail da pessoa a ser deletada: ");
        String email = scanner.nextLine();
        Pessoa pessoa = pessoaDao.buscarPorEmail(email);  // Buscar a pessoa pelo email antes de deletar
        if (pessoa != null && pessoaDao.deletar(pessoa)) {
            System.out.println("Pessoa deletada com sucesso.");
        } else {
            System.out.println("Pessoa não encontrada.");
        }
    }
}
