package org.zerock.ex03.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex03.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@RequestMapping("/sample")
@Controller
public class SampleController {

    @GetMapping("/ex01")
    public void ex1() {
        log.info("ex1");
    }

    @GetMapping({"/ex02", "/exlink"})
    public void ex2(Model model) {
        log.info("ex2");

        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("First " + i)
                    .last("last " + i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }
    @GetMapping("/exInline")
    public String exInline(RedirectAttributes redirectAttributes) {

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First : 100")
                .last("Last : 100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex03";
    }

    @GetMapping("/ex03")
    public void ex03() {}

    @GetMapping({"/exLayout", "/exLayout2", "exTamplate", "exSidebar"})
    public void exLayout() {}
}
