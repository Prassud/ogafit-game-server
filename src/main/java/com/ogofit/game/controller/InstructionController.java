package com.ogofit.game.controller;


import com.ogofit.game.models.Instruction;
import com.ogofit.game.service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class InstructionController {

    private final InstructionService instructionService;

    @MessageMapping("/clients/{clientId}/instructions")
    @SendTo("/topic/clients/{clientId}/instructions")
    public Instruction create(@DestinationVariable String clientId,
                              @Payload Instruction instruction) throws Exception {
        return instructionService.createInstruction(instruction);
    }

    @MessageMapping("/clients/{clientId}/instructions/verify")
    @SendTo("/topic/clients/{clientId}/instructions/verify")
    public Instruction verify(@DestinationVariable String clientId,
                              @Payload Instruction instruction) throws Exception {
        return instructionService.verify(instruction);
    }

}
