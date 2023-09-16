# Chatter
 Chatter - Custom chat plugin with Luckperms hooks
 
 Chatter is a custom Chat Plugin with built in custom join / leave message support, nickname support and luckperms prefix integration.
 
 Installation:
 1. Download the comiled .jar file (found in the "compiled" folder.
 2. Put the file in your plugins folder.
 3. Restart your server.
 4. There will now be a folder created called "Chatter", containing a few data files and a file named "config.yml", open "config.yml" and edit this to suit what you need.
 5. Once you've edited the config, use "/chreload" in-game to reload the config.
 6. Done!
 
 Commands:
  - /nick (set|who|list|reset|clear)
   - Alias: /nickname
  - /jmessage (set|get|reset|clear)
   - Alias: /joinmessage
  - /lmessage (set|get|reset|clear)
   - Alias: /leavemessage
  - /chreload
  
Permissions:
 - chatter.mod - Allows use of /jmessage clear & /lmessage clear & /nick clear
 - chatter.jmessage - Allows use of /jmessage set
 - chatter.lmessage - Allows use of /lmessage set
 - chatter.nick - Allows use of /nick set
 - chatter.reload - Allows the use of /chreload
 - chatter.nickcc - Allows chat colours in nicknames!