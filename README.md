# btelnyy-nochatreport
 Prevent Minecrafts new Chat reports from working while allowing for secure chat
 
 Report issues on GitHub or on [Discord](https://discord.gg/P22tFkjTm3)
 
 View the spigot resource [Here](https://www.spigotmc.org/resources/nochatreport.102932/)
## Commands:
> `/nochatreport`
- Toggle if you want your chat replaced with server messages
- Permission: btelnyy.command.nochatreport
## Config:
> `everyone_system_messages (bool)`
- Force everyone's messages to be replaced with server messages

> `operator_forced_to_use (bool)`
- Operators will be required to have their chat replaced

> `operator_add_to_list_on_join`
- Force operators to be added to the list of players which have their messages replaced when they join

> `replace_permission`
- People with this permission can have their messages replaced

> `permission_add_to_list_on_join`
- Anyone with the above permission will automatically be added to the list of people which have their messages replaced.
