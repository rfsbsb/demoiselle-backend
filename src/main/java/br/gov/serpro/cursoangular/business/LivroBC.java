package br.gov.serpro.cursoangular.business;

import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.serpro.cursoangular.entity.Autor;
import br.gov.serpro.cursoangular.entity.Livro;
import br.gov.serpro.cursoangular.persistence.LivroDAO;

import javax.inject.Inject;
import java.util.List;

public class LivroBC extends DelegateCrud<Livro, Long, LivroDAO> {

    @Inject
    private AutorBC autorBC;

    @Transactional
    public void load() {
        if (findAll().isEmpty()) {
            Autor jv = autorBC.find("Julio Verne").get(0);
            Autor jrrt = autorBC.find("J.R.R Tolkien").get(0);
            insert(new Livro("Viagem ao Centro da Terra", 200, "A história de jovens que viajam ao centro da terra.", jv));
            insert(new Livro("A Volta ao Mundo em 80 dias", 320, "Uma competição ao redor do mundo", jv));
            insert(new Livro("O Hobbit", 295, "Pequenos seres de pés peludos ajudando anões a enfrentar um dragão.", jrrt));
            insert(new Livro("O Senhor dos Anéis", 1200, "Uma jornada épica contra o mal.", jrrt));

        }
    }

    public List<Livro> find(String filter) {
        return getDelegate().find(filter);
    }

    @Transactional
    public Livro salvar(Livro livro) {
        livro.setAutor(autorBC.salvar(livro.getAutor()));

        if (livro.getId() == null) {
            return getDelegate().insert(livro);
        } else {
            return getDelegate().update(livro);
        }

    }
}
