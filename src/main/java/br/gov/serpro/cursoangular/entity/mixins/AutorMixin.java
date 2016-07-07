package br.gov.serpro.cursoangular.entity.mixins;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public abstract class AutorMixin {
    AutorMixin(@JsonProperty("id") int id, @JsonProperty("nome") int nome) { }

    @JsonProperty("id")
    abstract int getId();
    @JsonProperty("nome")
    abstract int getNome();
    @JsonIgnore
    abstract int getNacionalidade();
}
