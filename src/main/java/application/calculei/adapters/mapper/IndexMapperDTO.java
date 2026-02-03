package application.calculei.adapters.mapper;

import application.calculei.domain.dto.IndexRequestDTO;
import application.calculei.domain.dto.IndexResponseDTO;
import application.calculei.domain.models.Index;

public class IndexMapperDTO {

    public Index toDomain(IndexRequestDTO dto){
        return new Index(dto.id(), dto.nome(), dto.fator(), dto.valor(), dto.dataInit());
    }

    public IndexResponseDTO toDTO(Index index){
        return new IndexResponseDTO(index.getNome(), index.getValor(), index.getFator(), index.getDataInit());
    }
}
