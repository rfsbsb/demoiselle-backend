package br.gov.serpro.cursoangular.persistence;

import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.serpro.cursoangular.entity.Autor;
import br.gov.serpro.cursoangular.entity.Livro;

import javax.persistence.TypedQuery;
import java.util.List;

public class AutorDAO extends JPACrud<Autor, Long> {

    public List<Autor> find(String filter) {
        StringBuffer ql = new StringBuffer();
        ql.append("  from Autor b ");
        ql.append(" where lower(b.nome) like :nome ");

        TypedQuery<Autor> query = getEntityManager().createQuery(ql.toString(), Autor.class);
        query.setParameter("nome", "%" + filter.toLowerCase() + "%");

        return query.getResultList();
    }
}
