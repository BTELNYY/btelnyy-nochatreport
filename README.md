# btelnyy-nochatreport
 Prevent Minecrafts new Chat reports from working while allowing for secure chat
 
 Report issues on GitHub or on [Discord](https://discord.gg/P22tFkjTm3)
 
 View the spigot resource [Here](https://www.spigotmc.org/resources/nochatreport.102932/)
## Attention Server Owners!
* If you can, please test and report issues about this plugins compatibility with your plugins
* Report any issues you see here or Discord
## Commands:
> `/nochatreport`
- Toggle if you want your chat replaced with server messages
- Permission: `btelnyy.command.nochatreport`
> `/msg <player> <message>` (also works with `/w`)
- Send a non-reportable message
- Has the side effect of automatically correcting spaceing mistakes
- Permission: `btelnyy.command.msg`
### Quick Note about ignoring: it only works when the users messages are being replaced by the plugin
> `/ignore <player>`
- Prevents messages from this player from being displayed to you
- Operators and people with the permission `no_ignore_permission` cannot be ignored
- Permission: `btelnyy.command.ignore`
> `/unignore <player>`
- Allows messages from this player to be displayed to you
- Permission: `btelnyy.command.unignore`
> `/ignorelist`
- Show who you are ignoring
- Permission: `btelnyy.command.ignorelist`
> `/me <action>`
- Send a non-reportable /me
- Has the side effect of automatically correcting spaceing mistakes
- Permission: `btelnyy.command.me`
## Config:
> `everyone_system_messages: (true/false)`
- Default: false
- Force everyone's messages to be replaced with server messages

> `operator_forced_to_use: (true/false)`
- Default: false
- Operators will be required to have their chat replaced

> `operator_add_to_list_on_join: (true/false)`
- Default: true
- Force operators to be added to the list of players which have their messages replaced when they join

> `permission_add_to_list_on_join: (true/false)`
- Default: true
- Anyone with the above permission will automatically be added to the list of people which have their messages replaced.

> `replace_permission: (permission)`
- Default: "btelnyy.replacemessages"
- People with this permission can have their messages replaced

> `no_ignore_permission` (permission)
- Prevent players with this permission from being ignored with `/ignore`
