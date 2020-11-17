package com.ogofit.game.store.inmemory;

import com.ogofit.game.cache.Cache;
import com.ogofit.game.cache.CacheManager;
import com.ogofit.game.models.Instruction;
import com.ogofit.game.store.IInstructionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class InMemoryInstructionRepository implements IInstructionRepository {

    private static final String INSTRUCTION_CACHE = "INSTRUCTION_CACHE";

    private final CacheManager cacheManager;

    @Override
    public void add(Instruction instruction) {
        String clientId = instruction.getClient().getId().toString();
        Cache cache = cacheManager.getCache(INSTRUCTION_CACHE);
        List<Instruction> instructions = ofNullable((List<Instruction>) cache.get(clientId))
                .orElse(new CopyOnWriteArrayList<>());
        instructions.add(instruction);
        cache.put(clientId, instructions);
    }

    public List<Instruction> get(String clientId) {
        Cache cache = cacheManager.getCache(INSTRUCTION_CACHE);
        return ofNullable((List<Instruction>) cache.get(clientId))
                .orElse(new CopyOnWriteArrayList<>());
    }

    @Override
    public void delete(Instruction instruction) {
        String clientId = instruction.getClient().getId().toString();
        Cache cache = cacheManager.getCache(INSTRUCTION_CACHE);
        List<Instruction> instructions = ofNullable((List<Instruction>) cache.get(clientId))
                .orElse(new CopyOnWriteArrayList<>());
        instructions.remove(instruction);
    }
}
