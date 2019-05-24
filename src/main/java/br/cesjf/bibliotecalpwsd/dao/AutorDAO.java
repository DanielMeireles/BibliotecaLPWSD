/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Autor;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author dmeireles
 */
public class AutorDAO implements Serializable {
    
    public static AutorDAO autorDAO;

    public static AutorDAO getInstance() {
        if (autorDAO == null) {
            autorDAO = new AutorDAO();
        }
        return autorDAO;
    }
    
    public Autor buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Autor a WHERE a.id = :id");
            query.setParameter("id", id);
            Autor autor = (Autor) query.getSingleResult();
            if(autor != null && autor.getId() > 0) {
                return autor;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados autores!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados autores!", e.getMessage());
            return null;
        }
    }
    
    public Autor buscar(Autor a){
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Autor a WHERE a.id = :id");
            query.setParameter("id", a.getId());
            Autor autor = (Autor) query.getSingleResult();
            if(autor != null && autor.getId() > 0) {
                return autor;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados autores!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados autores!", e.getMessage());
            return null;
        }
    }

    public List<Autor> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Autor a");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados autores!", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public String remover(Autor autor) {
        try {
            List<Livro> livros;
            EntityManager em = PersistenceUtil.getEntityManager();
            try{
                Query query = em.createQuery("SELECT l FROM Livro l WHERE l.autorList.id = :id");
                query.setParameter("id", autor.getId());
                livros = query.getResultList();
            } catch (Exception e) {
                System.out.println(e);
                livros = new ArrayList<>();
            }
            if(livros.isEmpty() || livros == null){
                em.getTransaction().begin();
                autor = em.merge(autor);
                em.remove(autor);
                em.getTransaction().commit();
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Autor removido com sucesso!");
                return "Autor removido com sucesso!";
            } else {
                return "Não foi possível remover o autor, pois está vinculado a um ou mais livros";
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o autor!", e.getMessage());
            return "Não foi possível remover o autor, pois está vinculado a um ou mais livros";
        }
    }

    public String persistir(Autor autor) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            autor = em.merge(autor);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Autor salvo com sucesso!");
            return "Autor salvo com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o autor!", e.getMessage());
            if(e.getMessage().contains("nome_UNIQUE")){
                return "Não foi possível salvar o autor, pois o nome deve ser único";
            }
            return "Não foi possível salvar o autor!";
        }
    }

    public String removeAll() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Autor");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Todos os autores foram deletados!");
            return "Todos os autores foram deletados!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os autores!", e.getMessage());
            return "Não foi possível deletar todos os autores!";
        }
    }
    
}