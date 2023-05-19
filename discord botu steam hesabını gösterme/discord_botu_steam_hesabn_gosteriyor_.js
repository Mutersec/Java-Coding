const { MessageEmbed } = require("discord.js");
const config = require("../config.js");
const request = require('request');
const steam2 = require("steamidconvert")();
const SteamAPI = require('steamapi');
const steamapi = new SteamAPI(config.steamapi);

exports.run = async (client, message, args) => {
  const steamisim = args[0];
  if (!steamisim) return message.reply("Bir steam kullanıcısı ismi gir.");

  request(`https://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key=${config.steamapi}&vanityurl=${steamisim}`, function (error, response, body) {
    const json_body = JSON.parse(body);
    const id = json_body.response.steamid;

    request(`http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=${config.steamapi}&steamids=${id}`, function (error, response, body) {
      const json_body2 = JSON.parse(body);
      const player = json_body2.response.players[0];

      const nick = player.personaname;
      const durum = player.personastate;
      const ulke = player.loccountrycode;
      const create = player.timecreated;
      const oturum = player.lastlogoff;
      const pp = player.avatarfull;
      const ids = player.steamid;

      steamapi.getUserSummary(ids).then(summary => {
        const embed = new MessageEmbed()
          .setAuthor(nick, pp)
          .setDescription(`Nickname: **${nick}**\nDurum: **${getDurumString(durum)}**\nÜlke: **${ulke || "Bulunamadı"}**\nSteamID: [${steam2.convertToText(id)}](${summary.url})\n\nHesap Oluşturulma Tarihi: <t:${create}:f>\nSon Oturumu Kapatma Tarihi: ${oturum || "**Gizlide**"}`)
          .setColor("#ff0000")
          .setThumbnail(pp);

        message.channel.send({ embeds: [embed] });
      });
    });
  });
};

function getDurumString(durum) {
  const durumlar = ["Offline", "Online", "Busy", "Away", "Snooze", "Looking to trade", "Looking to play"];
  return durumlar[durum] || "Bilinmeyen";
}

exports.conf = {
  aliases: []
};

exports.help = {
  name: "komut"
};
