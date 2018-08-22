import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("/users")
class UserController {

    @Autowired
    UserService userService

    @RequestMapping(method = RequestMethod.GET)
    def getUserList(ModelMap map) {
        map.addAttribute("userList", userService.findAll())
        "userList"
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    def createUserForm(ModelMap map) {
        map.addAttribute("user", new User())
        map.addAttribute("action", "create")
        "userForm"
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    def postUser(ModelMap map, @ModelAttribute User user) {
        userService.insertByUser(user)
        return "redirect:/users/"
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    def getUser(@PathVariable Long id, ModelMap map) {
        map.addAttribute("user", userService.findById(id))
        map.addAttribute("action", "update")
        return "userForm"
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    def putUser(ModelMap map, @ModelAttribute User user) {
        userService.update(user)
        return "redirect:/users/"
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    def deleteUser(@PathVariable Long id) {
        userService.delete(id)
        return "redirect:/users/"
    }
}