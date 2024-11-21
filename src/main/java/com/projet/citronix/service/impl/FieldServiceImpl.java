package com.projet.citronix.service.impl;

import com.projet.citronix.dto.field.FieldRequestDTO;
import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.mapper.FieldMapper;
import com.projet.citronix.model.Farm;
import com.projet.citronix.model.Field;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    @Override
    public FieldResponseDTO createField(FieldRequestDTO fieldRequestDTO) {

        Farm farm = farmRepository.findById(fieldRequestDTO.farm_id())
                .orElseThrow(() -> new RuntimeException("Farm not found"));

        Field field = fieldMapper.toEntity(fieldRequestDTO);

        field.setFarm(farm);

        field = fieldRepository.save(field);

        return fieldMapper.toDTO(field);
    }

    @Override
    public List<FieldResponseDTO> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        return fields.stream().map(fieldMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public FieldResponseDTO getFieldById(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new RuntimeException("Field not found"));
        return fieldMapper.toDTO(field);
    }

    @Override
    public boolean deleteField(Long id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public FieldResponseDTO updateField(Long id, FieldRequestDTO fieldRequestDTO) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new RuntimeException("Field not found"));
        fieldMapper.toDTO(field);
        field = fieldRepository.save(field);
        return fieldMapper.toDTO(field);
    }
}