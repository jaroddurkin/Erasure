<div id="top"></div>

[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<br />
<div align="center">
  <a href="https://github.com/jaroddurkin/Erasure">
    <img src="img/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Erasure</h3>

  <p align="center">
    A Minecraft plugin to automate your hardcore survival worlds.
    <br />
    <a href="https://github.com/jaroddurkin/Erasure"><strong>View project website</strong></a>
    <br />
    <br />
    <a href="https://github.com/jaroddurkin/Erasure/issues">Report Bug / Request Features</a>
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#commands">Commands</a>
    </li>
    <li>
      <a href="#development">Development</a>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About The Project

Erasure is a Minecraft craftbukkit/spigot plugin that allows you to automate your hardcore survival worlds. On any player's death, the server will start a configured timer that will reset the world by closing the server. The previous world will also be deleted (optional functionality) when the server starts up again.

This plugin also collects per-player statistics on each world such as play time, mob kills, and more. When the server is reset, a CSV file will automatically be generated so you can collect whatever data you need.

<p align="right">(<a href="#top">Back to top</a>)</p>

### Built With

* [Maven](https://maven.apache.org/)
* [Spigot API](https://hub.spigotmc.org/javadocs/spigot/)
* [SQLite](https://www.sqlite.org/index.html)
* [JUnit](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org/)

<p align="right">(<a href="#top">Back to top</a>)</p>

## Commands

#### **/erasure stats (optional player)**

Prints out statistics for yourself or a given player including total number of deaths between all worlds, and some per-world statistics like play time.

#### **/erasure config (get|set) (option) (value if set)**

Allows moderators (if permission exists) to set configuration options without the need to change the configuration file and restart the server. Options include:

* resetTime - Time in minutes that the server will reset after a player dies
* deleteOnReset - Delete the entire previous world on server reset.
* messageOnDeath - Send a message to the entire server when a player dies

#### **/erasure cancel**

Allows moderators (if permission exists) to cancel a server reset after a player's death. Helps in the event of an accidental death or just testing things.

#### **/erasure reset**

Allows moderators (if permission exists) to manually trigger a server reset. The usual timer after a death will apply, then the server will reset as usual.

<p align="right">(<a href="#top">Back to top</a>)</p>

## Development

The development environment for this plugin is easy to set up. Simply ensure you have the Java 18 JDK, and Maven to install all dependencies. If you make changes, use `mvn package` to compile a new `.jar` file to be used for your server.

<p align="right">(<a href="#top">Back to top</a>)</p>

## Roadmap

- [ ] Feature Update 2 (v0.2)
- [ ] Plugin Beta Release (v0.3)

<p align="right">(<a href="#top">Back to top</a>)</p>

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">Back to top</a>)</p>

## Contact

Jarod Durkin - [@jaroddurkin](https://twitter.com/jaroddurkin) - jarod@durkin.app

<p align="right">(<a href="#top">Back to top</a>)</p>

[forks-shield]: https://img.shields.io/github/forks/jaroddurkin/Erasure.svg?style=for-the-badge
[forks-url]: https://github.com/jaroddurkin/Erasure/network/members
[stars-shield]: https://img.shields.io/github/stars/jaroddurkin/Erasure.svg?style=for-the-badge
[stars-url]: https://github.com/jaroddurkin/Erasure/stargazers
[issues-shield]: https://img.shields.io/github/issues/jaroddurkin/Erasure.svg?style=for-the-badge
[issues-url]: https://github.com/jaroddurkin/Erasure/issues
[license-shield]: https://img.shields.io/github/license/jaroddurkin/Erasure.svg?style=for-the-badge
[license-url]: https://github.com/jaroddurkin/Erasure/blob/master/LICENSE.txt
