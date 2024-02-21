/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sin.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import com.sin.model.Pessoa;

/**
 *
 * @author steli sumeid's branch
 */
public class GenericDAO {

    private EntityManagerFactory fabrica;
    private EntityManager gerente;

    public GenericDAO() {
        fabrica = Persistence.createEntityManagerFactory("SystemPU");
        gerente = fabrica.createEntityManager();
    }

    public void add(Object obj) {
        fabrica = Persistence.createEntityManagerFactory("SystemPU");
        gerente = fabrica.createEntityManager();
        try {
            gerente.getTransaction().begin();
            gerente.persist(obj);
            gerente.getTransaction().commit();
            gerente.close();
           // JOptionPane.showMessageDialog(null, "Gravado");
        } catch (Exception e) {
            gerente.getTransaction().rollback();
            e.printStackTrace();
           // JOptionPane.showMessageDialog(null, "Erro ao gravar");
        }
    }

    /**
     * Metodo para listar todos do banco Pode colocar a classe pois ele filtra
     * por ser generico
     *
     * @param classe
     * @return
     */
    public List<?> listar(Class<?> classe) {

        try {
            fabrica = Persistence.createEntityManagerFactory("SystemPU");
            gerente = fabrica.createEntityManager();

            // Consulta para listar todos os objetos da classe especificada
            String consulta = "SELECT obj FROM " + classe.getSimpleName() + " obj";
            TypedQuery<?> query = gerente.createQuery(consulta, classe);

            List<?> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Ou lançar uma exceção apropriada, dependendo do seu caso.
        } finally {
            if (gerente != null && gerente.isOpen()) {
                gerente.close();
            }
        }
    }

    public List<Pessoa> listarTodosParaRelatorio(Class<Pessoa> classe) {
        try {
            TypedQuery<Pessoa> query = gerente.createNamedQuery("Pessoa.findAll", Pessoa.class);
            List<Pessoa> pessoas = query.getResultList();
            return pessoas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
// @NamedQuery(name = "Equipamento.findAll", query = "SELECT e FROM equipamento e")

    public int contar_Quantidade_Base(Class<?> classe) {

        try {
            fabrica = Persistence.createEntityManagerFactory("SystemPU");
            gerente = fabrica.createEntityManager();

            // Consulta para contar_Quantidade_Base todos os objetos da classe especificada
            String consulta = "SELECT COUNT(obj) FROM " + classe.getSimpleName() + " obj";
            TypedQuery<Long> query = gerente.createQuery(consulta, Long.class);

            Long resultado = query.getSingleResult();
            return resultado.intValue(); // Converter para int

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Ou lançar uma exceção apropriada, dependendo do seu caso.
        } finally {
            if (gerente != null && gerente.isOpen()) {
                gerente.close();
            }
        }
    }

    public long contarPessoasPorDTayp(String dtayp) {
        try {
            fabrica = Persistence.createEntityManagerFactory("SystemPU");
            gerente = fabrica.createEntityManager();
            TypedQuery<Long> query = gerente.createQuery("SELECT COUNT(p) FROM Pessoa p WHERE p.dtayp = :dtaypValue", Long.class);
            query.setParameter("dtaypValue", dtayp);
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            return -1;

        }
    }

    /**
     * Busca pelo Id
     *
     * @param objectoDaClasse
     * @param id
     * @return
     */
    public Object buscaId(Class<?> classe, Object id) {
        try {
            return gerente.find(classe, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo Para remover Logico
     *
     * @param classe
     * @param id
     * @param novosDados
     */
    public void removerLogico(Class<?> classe, Long id, Object novosDados) {
        try {
            fabrica = Persistence.createEntityManagerFactory("SystemPU");
            gerente = fabrica.createEntityManager();
            gerente.getTransaction().begin();

            Object obj = gerente.find(classe, id);
            if (obj != null) {
                gerente.merge(novosDados);
                gerente.getTransaction().commit();
            } else {
               // JOptionPane.showMessageDialog(null, "ID inserido não existe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (gerente != null && gerente.isOpen()) {
                gerente.close();
            }
        }
    }

    /**
     * Metodo para Atualizar
     *
     * @param classe
     * @param id
     * @param novosDados
     */
    public void Atualizar(Class<?> classe, Long id, Object novosDados) {
        try {
            fabrica = Persistence.createEntityManagerFactory("SystemPU");
            gerente = fabrica.createEntityManager();
            gerente.getTransaction().begin();

            Object obj = buscaId(classe, id);
//            Object obj = buscarPessoaPorId(id);
            if (obj != null) {
                gerente.merge(novosDados);
                gerente.getTransaction().commit();
            } else {
              //  JOptionPane.showMessageDialog(null, "ID inserido não existe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (gerente != null && gerente.isOpen()) {
                gerente.close();
            }
        }

    }

    /**
     * Remover De forma Definitiva
     *
     * @param classe
     * @param id
     */
    public void removeFisico(Class<?> classe, Long id) {
        try {
            fabrica = Persistence.createEntityManagerFactory("SystemPU");
            gerente = fabrica.createEntityManager();
            gerente.getTransaction().begin();

            Object obj = gerente.find(classe, id);
            if (obj != null) {
                gerente.remove(obj);
                gerente.getTransaction().commit();
            } else {
              //  JOptionPane.showMessageDialog(null, "ID insirido nao existe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            gerente.close();
        }
    }

    /*
    caso tenha varias tabelas para ter acesso
     */
    public <T> T buscarPorEmail(Class<T> entityClass, String email) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("SystemPU");
        EntityManager gerente = fabrica.createEntityManager();

        try {
            String queryName = entityClass.getSimpleName() + ".findByEmail";
            TypedQuery<T> query = gerente.createNamedQuery(queryName, entityClass)
                    .setParameter("email", email);
            List<T> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                return resultList.get(0);
            } else {
                return null;
            }
        } finally {
            gerente.close();
            fabrica.close();
        }
    }

    /*
    para tabela pessoa
     */
    //Usando para teste apenas Admin
    public Object logarEmail(String email) {
        fabrica = Persistence.createEntityManagerFactory("SystemPU");
        gerente = fabrica.createEntityManager();
        List<Pessoa> pessoa = gerente.createNamedQuery("Pessoa.findByEmail", Pessoa.class).setParameter("email", email).getResultList();
        if (!pessoa.isEmpty()) {
            return pessoa.get(0);
        } else {
            return null;
        }

    }
      public static boolean validarCodigo(String input) {
        // Cria um padrão de expressão regular para 4 letras seguidas de 8 números
        String padrao = "^[A-Za-z]{4}\\d{8}$";
        // Compila o padrão em um objeto Pattern
        Pattern pattern = Pattern.compile(padrao);
        // Cria um objeto Matcher para a entrada fornecida
        Matcher matcher = pattern.matcher(input);
        // Verifica se a entrada corresponde ao padrão
        return matcher.matches();
    }

    public Object logarEmailOuCodigo(String input) {
        fabrica = Persistence.createEntityManagerFactory("SystemPU");
        gerente = fabrica.createEntityManager();
        if (validarCodigo(input)) {
            List<Pessoa> pessoas = gerente.createNamedQuery("Pessoa.findByCodigo", Pessoa.class)
                    .setParameter("codigo", input)
                    .getResultList();
            if (!pessoas.isEmpty()) {
                return pessoas.get(0);
            }
        } else {
            List<Pessoa> pessoas = gerente.createNamedQuery("Pessoa.findByEmail", Pessoa.class)
                    .setParameter("email", input)
                    .getResultList();
            if (!pessoas.isEmpty()) {
                return pessoas.get(0);
            }
        }

        return null;
    }

    public List<Pessoa> buscarPessoasPorNome(String nome) {
        TypedQuery<Pessoa> query = gerente.createNamedQuery("Pessoa.findByName", Pessoa.class);
        query.setParameter("nome", "%" + nome + "%"); // O operador % é usado para consultas "LIKE"
        return query.getResultList();
    }

    /**
     * Metodos mais especificos caso haja algum problema
     */

    /*
    public Object buscaId(Long id) {
        try {
            return gerente.find(Pessoa.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     */

 /* 
    public Pessoa buscarPessoaPorId(Long id) {
    EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("aaaaaPU");
    EntityManager gerente = fabrica.createEntityManager();

    try {
        TypedQuery<Pessoa> query = gerente.createNamedQuery("Pessoa.findById", Pessoa.class);
        query.setParameter("id", id);
        return query.getSingleResult(); // Supondo que haja apenas uma pessoa com o ID fornecido
    } catch (NoResultException e) {
        // Trate o caso em que nenhuma pessoa com o ID fornecido foi encontrada
        return null;
    } finally {
        gerente.close();
        fabrica.close();
    }
    }
     */
//    Configuration configuration = new Configuration().configure("persistence.xml");
//ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//
//    
//    public List<Ficha_Inscricao> obterNotificacoesDeCadastroNaoLidas() {
//    Session session = sessionFactory.openSession();
//    try {
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<Ficha_Inscricao> query = builder.createQuery(Ficha_Inscricao.class);
//        Root<Ficha_Inscricao> root = query.from(Ficha_Inscricao.class);
//
//        // Critério para notificações não lidas de cadastro
//        Predicate criteria = builder.and(
//            //builder.equal(root.get("lida"), false),
//            builder.equal(root.get("funcionario"), "Funcionario maluco")
//        );
//
//        query.select(root).where(criteria);
//
//        return session.createQuery(query).getResultList();
//    } finally {
//        session.close();
//    }
//}
}
