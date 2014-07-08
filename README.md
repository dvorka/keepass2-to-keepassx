KeePass2 to KeePassX Convertor
==============================

[KeePass2](http://keepass.info/) to [KeePassX](http://www.keepassx.org) password database 
convertor.

Installation
------------

* You must have Java Runtime Environment (JRE) installed. Download and install Java from
  http://www.oracle.com/technetwork/java/javase/downloads/index.html
  or
  http://openjdk.java.net/install/

* Download KeePass2ToKeePassX release and extract it:
  https://github.com/dvorka/keepass2-to-keepassx/releases



Use
---

1. Open source database in KeePass2.
2. Use `menu/File/Export.../KeePass XML (2.x)` option.
3. Save it as for instance as `old-keepass2-database.xml`.
4. Change to `keepass2tokeepassx/bin` directory of this project.
5. Run ``./keepass2-to-keepassx.sh old-keepass2-database.xml new-keepassx-database.xml``
6. Start KeePassX.
7. Import `new-keepassx-database.xml` using `menu/Import from.../KeePassX XML (*.xml)`.
8. Delete `old-keepass2-database.xml` and `new-keepassx-database.xml` files.

And that's it!

Bugs
----

https://github.com/dvorka/keepass2-to-keepassx/issues?state=open
