package br.gov.serpro.cursoangular.business;

import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.serpro.cursoangular.entity.Autor;
import br.gov.serpro.cursoangular.persistence.AutorDAO;

import java.util.List;

public class AutorBC extends DelegateCrud<Autor, Long, AutorDAO> {

    @Startup
    @Transactional
    public void load() {
        if (findAll().isEmpty()) {
            insert(new Autor("Julio Verne", "França"));
            insert(new Autor("J.R.R Tolkien", "África do Sul"));
        }
    }

    public List<Autor> find(String filter) {
        return getDelegate().find(filter);
    }

    @Transactional
    public Autor salvar(Autor autor) {
        if (autor.getId() == null) {
            List<Autor> items = find(autor.getNome());
            if (items.size() > 0) {
                return items.get(0);
            } else {
                return getDelegate().insert(autor);
            }
        } else {
            if (autor.getNome() != null && !autor.getNome().isEmpty() && autor.getNacionalidade() != null && !autor.getNacionalidade().isEmpty()) {
                return getDelegate().update(autor);
            }
            return load(autor.getId());
        }

    }
}
