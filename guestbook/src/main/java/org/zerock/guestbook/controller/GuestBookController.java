package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestBookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping({"/"})
    public String index() {
        log.info("index");
        return "guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list work");

        model.addAttribute("list", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register work");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto : " + dto);

        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("read work : " + gno);

        GuestBookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);

    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        log.info("modify work : " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("gno", dto.getGno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String delete(Long gno, RedirectAttributes redirectAttributes) {
        log.info("delete work");

        service.delete(gno);

        redirectAttributes.addAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}
