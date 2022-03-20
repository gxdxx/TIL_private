package com.gxdxx.jpa.controller;

import com.gxdxx.jpa.domain.item.Book;
import com.gxdxx.jpa.domain.item.Item;
import com.gxdxx.jpa.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        itemService.saveItem(form.getName(), form.getPrice(), form.getStockQuantity, form.getAuthor, form.getIsbn());
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity(), item.getAuthor(), item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")    // id가 조작될 수 있기 때문에 item 권한이 있는지 체크하는게 좋다.
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }

}
