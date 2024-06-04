package com.example.literalura.principal;

import com.example.literalura.model.Autor;
import com.example.literalura.model.DadosLivro;
import com.example.literalura.model.Livro;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LivroRepository;
import com.example.literalura.service.ConsumoApi;
import com.example.literalura.service.ConvertData;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private LivroRepository livroRepository;
    private AutorRepository autorRepository;
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvertData convertData = new ConvertData();
    private List<Autor> autores = new ArrayList<>();
    private List<Livro> listaDeLivros = new ArrayList<>();


    public Principal(LivroRepository repository, AutorRepository autorRepository) {
        this.livroRepository = repository;
        this.autorRepository = autorRepository;

    }


    public void exibeMenu() {
        var opcao = -1;
        try {


            while (opcao != 0) {
                System.out.println("""
                        escolha uma opção:
                        1-Listar livro pelo titulo 
                        2-Listar livros registrados 
                        3-Listar Autores registrados
                        4-Listar autores vivos em determinado ano
                        5-listar livros por idioma
                        """);


                opcao = leitura.nextInt();
                leitura.nextLine();
                switch (opcao) {
                    case 1:
                        buscarPortitulo();
                        break;
                    case 2:
                        listarLivrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegsitrados();
                        break;
                    case 4:
                        listarAutoresVivosNoAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    default:
                        System.out.println("opção invalida!");
                        break;
                }

            }
        } catch (InputMismatchException e) {
            System.out.println("Erro! tipo de dado incorreto.");
            throw new RuntimeException(e);
        }
    }


    private void buscarPortitulo() {


        System.out.println("Digite o Titulo do livro procurado:");
        var titulo = leitura.nextLine().replace(" ", "%20");
        var json = consumoApi.obterDados("https://gutendex.com/books/?search=" + titulo);

        var jsonNode = convertData.getResultsJson(json);
        var result = convertData.convertJsonNodeToString(jsonNode);
        var dadosLivro = convertData.getDataList(result, DadosLivro.class);
        Livro livro = new Livro(dadosLivro.get(0));
        Autor autor = livro.getAutor();
        System.out.println(livro);
        Boolean encontrado = false;
        for (Autor a : autores) {
            if (a.getName().equals(autor.getName())) {
                encontrado = true;
                break; // Interrompe o loop assim que encontrar o autor
            }
        }
        Autor autorExistente = autorRepository.findByName(autor.getName());
        if (autorExistente == null) {
            autorRepository.save(autor);
        } else {
            // Se o autor já existe, atribui o autor  ao livro
            livro.setAutor(autorExistente);
        }

        var livros = livroRepository.findAll();
        boolean livroJaNoBanco= false;
        for (Livro l : livros) {
            if (l.getTitulo().equals(livro.getTitulo())){
                livroJaNoBanco=true;
            }
        }
        if(!livroJaNoBanco){
            livroRepository.save(livro);
        }



    }

    private void listarLivrosRegistrados() {
        var livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutoresRegsitrados() {
        autores = autorRepository.findAll();
        autores.forEach(a -> a.imprimirLivros());

    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o Ano: ");
        var ano = leitura.nextInt();
        leitura.nextLine();
        autores = autorRepository.findAuthorsByYear(ano);
        if (autores.size() == 0) System.out.println("Não foram encontrados autores vivos no ano escolhido. ");
        autores.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("*********************");
        System.out.println("escolha o idioma: ");
        System.out.println("Digite pt  para portugues \nDigite fr para frances \nDigite en para ingês \nDigite es para  espanhol");
        System.out.println("*********************");
        var idioma = leitura.nextLine().toLowerCase().trim();
        listaDeLivros = livroRepository.findByIdioma(idioma);
        if (listaDeLivros.size() == 0) System.out.println("Nenhum livro encontrado para o idoma informado.");
        listaDeLivros.forEach(System.out::println);

    }


}
