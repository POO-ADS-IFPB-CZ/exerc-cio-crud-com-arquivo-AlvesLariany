package dao;

import model.Pessoa;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class PessoaDao {

    private File arquivo;

    public PessoaDao() {
        arquivo = new File("pessoas.ser");

        // Verifica se o arquivo existe, se não, cria um novo
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Falha ao criar arquivo");
            }
        }
    }


    public Set<Pessoa> getPessoas() {
        if (arquivo.length() > 0) {
            // Há dados no arquivo, ler o conjunto de pessoas
            try{
                FileInputStream inputStream = new FileInputStream(arquivo);
                 ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Set<Pessoa> pessoas=
                        (Set<Pessoa>) objectInputStream.readObject();
                return pessoas;
            } catch (FileNotFoundException e) {
                System.out.println("Arquivo não encontrado");
            } catch (IOException e) {
                System.out.println("Falha ao ler arquivo");
            } catch (ClassNotFoundException e) {
                System.out.println("Falha ao ler arquivo");
            }
        }
        // Se não houver dados no arquivo, retorna um novo conjunto vazio
        return new HashSet<>();
    }

    // Método para salvar uma pessoa no arquivo
    public boolean salvar(Pessoa pessoa) {
        Set<Pessoa> pessoas = getPessoas();
        if (pessoas.add(pessoa)) { // Adiciona a pessoa se o e-mail não for duplicado
            try {
                FileOutputStream outputStream = new FileOutputStream(arquivo);
                ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(pessoas);
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("Arquivo não encontrado");
            } catch (IOException e) {
                System.out.println("Falha ao escrever no arquivo");
            }
        }
        return false;
    }

    public boolean deletar(Pessoa pessoa) {
        Set<Pessoa> pessoas = getPessoas();
        if (pessoas.remove(pessoa)) {
            try {
                FileOutputStream outputStream = new FileOutputStream(arquivo);
                ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(pessoas);
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("Arquivo não encontrado");
            } catch (IOException e) {
                System.out.println("Falha ao escrever no arquivo");
            }
        }
        return false;
    }


    public Pessoa buscarPorEmail(String email) {
        Set<Pessoa> pessoas = getPessoas();
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getEmail().equals(email))
                return pessoa;

        }
        return null;
    }


    public boolean atualizar(Pessoa pessoa) {
        return deletar(pessoa) && salvar(pessoa);
    }
}
