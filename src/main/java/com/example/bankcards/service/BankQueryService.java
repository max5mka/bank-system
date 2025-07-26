package com.example.bankcards.service;

import com.example.bankcards.controller.api.request.BankQueryRequest;
import com.example.bankcards.dao.dto.BankQueryDto;
import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.entity.BankQuery;
import com.example.bankcards.dao.enums.BankQueryStatus;
import com.example.bankcards.dao.enums.BankQueryType;
import com.example.bankcards.dao.enums.CardStatus;
import com.example.bankcards.dao.mapping.BankQueryMapping;
import com.example.bankcards.dao.repository.BankQueryRepository;
import com.example.bankcards.exception.BankQueryNotFoundException;
import com.example.bankcards.service.validation.BankQueryValidation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class BankQueryService {

    private final UserService userService;
    private final BankQueryRepository bankQueryRepository;
    private final BankQueryMapping bankQueryMapping;
    private final CardService cardService;
    private final BankQueryValidation bankQueryValidation;
    private Map<BankQueryType, Consumer<BankQuery>> approvedHandlers;

    @PostConstruct
    public void initHandler() {
        approvedHandlers = Map.of(
                BankQueryType.CREATE_CARD, entity -> {
                    CardDto newCard = cardService.create(entity.getUserId());
                    entity.setCardId(newCard.getId());
                },
                BankQueryType.BLOCK_CARD, entity ->
                        cardService.editStatus(entity.getCardId(), CardStatus.BLOCKED),
                BankQueryType.ACTIVATE_CARD, entity ->
                        cardService.editStatus(entity.getCardId(), CardStatus.ACTIVE)
        );
    }

    public BankQueryDto getById(Long id) {
        return bankQueryRepository.findById(id)
                .map(bankQueryMapping::toDto)
                .orElseThrow(() -> new BankQueryNotFoundException(id));
    }

    public List<BankQueryDto> getAll() {
        return bankQueryRepository.findAll().stream()
                .map(bankQueryMapping::toDto).toList();
    }

    public BankQueryDto create(String login, Long cardId, BankQueryType type) {
        var userDto = userService.findByLogin(login);
        bankQueryValidation.validateCardAccess(cardId, type, userDto.getId());
        return createAndSave(userDto.getId(), cardId, type, BankQueryStatus.PENDING);
    }

    public BankQueryDto create(BankQueryRequest request) {
        bankQueryValidation.validateUserExists(request.getUserId());
        bankQueryValidation.validateCardAccess(
                request.getCardId(), request.getBankQueryType(), request.getUserId());

        return createAndSave(request.getUserId(), request.getCardId(),
                request.getBankQueryType(), request.getBankQueryStatus());
    }

    public BankQueryDto changeStatus(Long queryId, BankQueryStatus newStatus) {
        BankQuery entity = bankQueryRepository.findById(queryId)
                .orElseThrow(() -> new BankQueryNotFoundException(queryId));

        bankQueryValidation.validateStatusTransition(entity.getBankQueryStatus(), newStatus);
        entity.setBankQueryStatus(newStatus);

        if (newStatus == BankQueryStatus.APPROVED) {
            Optional.ofNullable(approvedHandlers.get(entity.getBankQueryType()))
                    .ifPresent(handler -> handler.accept(entity));
        }

        bankQueryRepository.save(entity);
        return bankQueryMapping.toDto(entity);
    }

    private BankQueryDto createAndSave(Long userId, Long cardId,
                                       BankQueryType type,
                                       BankQueryStatus status) {
        BankQuery entity = new BankQuery(userId, cardId, type, status);
        bankQueryRepository.save(entity);
        return bankQueryMapping.toDto(entity);
    }
}
