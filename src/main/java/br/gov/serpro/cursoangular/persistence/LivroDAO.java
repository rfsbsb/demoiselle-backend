package br.gov.serpro.cursoangular.persistence;

import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.serpro.cursoangular.entity.Livro;

import javax.persistence.TypedQuery;
import java.util.List;

public class LivroDAO extends JPACrud<Livro, Long> {

    public List<Livro> find(String filter) {
        StringBuffer ql = new StringBuffer();
        ql.append("  from Livro b ");
        ql.append(" where lower(b.titulo) like :titulo ");

        TypedQuery<Livro> query = getEntityManager().createQuery(ql.toString(), Livro.class);
        query.setParameter("titulo", "%" + filter.toLowerCase() + "%");

        return query.getResultList();
    }
}
