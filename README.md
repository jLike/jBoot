jBooter server-side library
----------------------------

[![Build Status][travis-image]][travis-url] [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.jLike/jbooter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.jLike/jbooter)

This project is used by the jBooter generator, for more information, issues or questions, please go to [jLike/generator-jbooter](https://github.com/jLike/generator-jbooter).

[travis-image]: https://travis-ci.org/jLike/jbooter.svg?branch=master
[travis-url]: https://travis-ci.org/jLike/jbooter


## Genkey

<pre>

 gpg --gen-key
 
 choice RSA only 2048 bits

</pre>

## Vim settings

<pre>
vim settings.xml

 <server>
         <id>ossrh</id>
         <username>your username </username>
         <password>your password </password>
 </server>
 
</pre>

## Deploy
<pre>

mvn -Dgpg.passphrase=<your passphase> deploy
</pre>

## List key

<pre>

gpg2 --list-keys

</pre>

## SEND KEY
<pre>
gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys KEY
gpg --keyserver hkp://keyserver.ubuntu.com --send-keys KEY
</pre>