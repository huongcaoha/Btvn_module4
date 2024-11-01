package com.ra.session01.controller;

import com.ra.session01.model.entity.Account;
import com.ra.session01.model.entity.Product;
import com.ra.session01.model.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String index(Model model){
        List<Account> accounts  = accountService.findAll();
        model.addAttribute("accounts",accounts);
        return "/account/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("account",new Account());
        return "/account/create";
    }

    @PostMapping("/create")
    public String add(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes, Model model){
        List<Account> accounts = accountService.findAll();
        boolean checkAccountPhone = accounts.stream().anyMatch(account1 -> account1.getPhone().equalsIgnoreCase(account.getPhone()));
        if(checkAccountPhone){
            model.addAttribute("rsCreate",false);
            model.addAttribute("account",account);
            return "/account/create";
        }else {
            Account account1 = accountService.saveAccount(account);
            if(account1 != null){
                redirectAttributes.addFlashAttribute("rsCreate",true);
                return "redirect:/account";
            }else {
                model.addAttribute("rsCreate",false);
                model.addAttribute("account",account);
                return "/account/create";
            }
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable long id, Model model){
        Account account = accountService.findById(id);
        model.addAttribute("account",account);
        return "/account/update";
    }

    @PostMapping("/update/{id}")
    public String edit(@PathVariable long id , @ModelAttribute("account") Account account , Model model,
                       RedirectAttributes redirectAttributes){
        account.setId(id);
        String oldAccountPhone = accountService.findById(id).getPhone();
        List<Account> accounts = accountService.findAll();
        boolean checkAccountPhone = accounts.stream().anyMatch(account1 -> account1.getPhone().equalsIgnoreCase(account.getPhone()));
        if(checkAccountPhone){
            if (account.getPhone().equalsIgnoreCase(oldAccountPhone)){
                checkAccountPhone = false;
            }
        }
        if(checkAccountPhone){
            model.addAttribute("rsUpdate",false);
            model.addAttribute("account",account);
            return "/account/update";
        }else {
            Account account1 = accountService.saveAccount(account);
            if(account1 != null){
                redirectAttributes.addFlashAttribute("rsUpdate",true);
                return "redirect:/account";
            }else {
                model.addAttribute("rsUpdate",false);
                model.addAttribute("account",account);
                return "/account/update";
            }
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        Account account = accountService.findById(id);
        accountService.deleteAccount(account);
        return "redirect:/account";
    }
}
