# CCBT Mod Pack Checker for Minecraft

## Introduction

The Mod Pack Checker is a Minecraft mod designed to ensure your mod pack is always in sync with the latest server updates. It manages mod files, automatically downloads missing mods, and keeps players informed with the latest server news and updates to ensure they can keep playing.

## Overview

This mod aims to improve your Minecraft experience by automating the mod synchronization process. It compares the mods in your client's `/mods` folder with those required by the server, downloading any missing mods if you choose to enable this feature. Additionally, it provides helpful user interfaces for updates and server news. It uses an API I wrote on python (Flask and Gunicorn), I recommend you edit the code and make it communicate with your own, mine is too hacky to share right now.

## Features

- **Automatic Mod Synchronization**: Compares your local mod files with the server's list (sends request to the API to get a list, it does not send the users mod list) and identifies discrepancies.
- **Mod Downloading**: Automatically downloads and installs missing mods as per server requirements. Must be enabled by user.
- **Server News Updates**: Keeps you updated with the latest news and announcements from the server.
- **Update Notifications**: Alerts you when a game restart is needed to apply new updates.
- **User-Friendly Interface**: It uses [ModMenu](https://github.com/TerraformersMC/ModMenu) to let the user manage the mod configuration.

## Getting Started

![Screenshots](https://files.cocobut.net/screenshots.png)

### Initial Setup

On the first launch, you'll need to enable the mod's functionalities manually. This ensures you understand and agree to the mod reading the users files in the mods folder and communicating to an external API as described here and on the disclaimer below.

### Enabling the Mod

You can activate the mod directly through the game's interface or by editing the `modpackchecker.json` configuration file in the `.\minecraft\config` directory.

#### Configuration Example

jsonCopy code

```json
{
  "turnOn": true,
  "autoDownload": true,
  "notifyUpdate": true,
  "APIDownloadUrl": "https://api.cocobut.net/download/",
  "APIListUrl": "https://api.cocobut.net/modpack-checker?action\u003dlistfiles",
  "useCustomFolder": false,
  "customModFolder": ""
}
```

### Dependencies

Ensure you have the following dependencies installed:

- Cloth Config Fabric (version 6.5.102 or later)
- ModMenu (version 3.2.5 or later)

## Usage

After enabling, the Mod Pack Checker will automatically compare your local mods with the server's list. If any mods are missing and auto-download is enabled, they will be downloaded and installed. You will be notified if a game restart is necessary to apply updates.

## Customization

You can customize the mod's behavior by editing the `modpackchecker.json` file. This includes turning on/off automatic downloads, specifying custom mod folders, and setting API URLs or by using ModMenu.

## Disclaimer

### Important Notes

- By using Mod Pack Checker, you agree to automatic file management and, if enabled, mod downloads.
- The mod respects your privacy and does not collect personal data. It only accesses files within the Minecraft directory.
- This project strives for secure and relevant downloads but advise users to proceed with caution and back up their data regularly.
- This mod complies with Minecraft's EULA and does not alter game mechanics.

## Support and Contributions

This is both my first mod and public GitHub repo, all help is welcome.

<p xmlns:cc="http://creativecommons.org/ns#" xmlns:dct="http://purl.org/dc/terms/"><a property="dct:title" rel="cc:attributionURL" href="https://github.com/h22679/ccbt-modpack-checker">CCBT Modpack Checker</a> by <a rel="cc:attributionURL dct:creator" property="cc:attributionName" href="https://github.com/h22679">Brais__</a> is licensed under <a href="http://creativecommons.org/licenses/by-nc/4.0/?ref=chooser-v1" target="_blank" rel="license noopener noreferrer" style="display:inline-block;">CC BY-NC 4.0<img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/cc.svg?ref=chooser-v1"><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/by.svg?ref=chooser-v1"><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/nc.svg?ref=chooser-v1"></a></p>
