package com.mshop.app.attribute.service.impl;

import com.mshop.app.attribute.model.Attribute;
import com.mshop.app.attribute.repository.AttributeRepository;
import com.mshop.app.attribute.service.AttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;

    @Override
    public Attribute create(Attribute attribute) {
        log.info("Create attribute:{}",attribute);
        return attributeRepository.save(attribute);
    }
}
