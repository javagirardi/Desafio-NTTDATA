package trilha.back.financys.services;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import trilha.back.financys.DTO.LancamentosDTO;
import trilha.back.financys.entities.Category;
import trilha.back.financys.entities.Lancamentos;
import trilha.back.financys.exceptions.DivisaoZeroException;
import trilha.back.financys.repositories.CategoryRepository;
import trilha.back.financys.repositories.LancamentosRepository;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LancamentosService {

    private final LancamentosRepository lancamentosRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    private List<LancamentosDTO> lancamentosDTOList = new ArrayList<>();

    public boolean validadeCategoryByIdL(long idCategory) {
        Optional<Category> category = categoryRepository.findById(idCategory);
        return category.isPresent();
    }

    public Lancamentos create(Lancamentos lancamentos) {
        boolean categoriaExistente = validadeCategoryByIdL(lancamentos.getCategory().getId());
        if (categoriaExistente == false) {
            System.out.println("================!CATEGORIA NÃO ENCONTRADA!===============");

        }
        return lancamentosRepository.save(lancamentos);
    }

//    public ResponseEntity<List<Lancamentos>> findAll (Boolean paid){
//        List<Lancamentos> lancamentos = new ArrayList<>();
//        if (Objects.isNull(paid)){
//            lancamentos = lancamentosRepository.findAll();
//        }else{
//            lancamentos = lancamentosRepository.findByPaid(paid);
//        }
//        return ResponseEntity.ok(lancamentos);
//    }

    public ResponseEntity<Lancamentos> read (Long id){
        Lancamentos read = lancamentosRepository.findById(id).get();
        return ResponseEntity.ok(read);
    }

    public ResponseEntity<Lancamentos> update(Long id, Lancamentos lancamentos){
        lancamentos.setId(id);
        lancamentosRepository.save(lancamentos);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> deleteLancamentos(Long id){
        lancamentosRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private LancamentosDTO mapToDto(Lancamentos lancamentos) {
        LancamentosDTO lancamentosDTO = mapper.map(lancamentos, LancamentosDTO.class);
        return lancamentosDTO;
    }

    private Lancamentos mapToEntity(LancamentosDTO lancamentosDTO) {
        Lancamentos lancamentos = mapper.map(lancamentosDTO, Lancamentos.class);
        return lancamentos;
    }

    public List<LancamentosDTO> retornarListaDto() {
        List<Lancamentos> listaLancamentos = lancamentosRepository.findAll();
        lancamentosRepository.getClass();
        return listaLancamentos.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Integer calculaMedia(Integer x, Integer y) {
        try {
            return (x / y);
        } catch (ArithmeticException e) {
            throw new DivisaoZeroException("Na matemática, não se pode dividir por zero --- " + e);
        }
    }

    public Lancamentos save(Lancamentos lancamentos){
        return lancamentosRepository.save(lancamentos);

    }
}


