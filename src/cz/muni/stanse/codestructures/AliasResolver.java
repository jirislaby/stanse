package cz.muni.stanse.codestructures;

import java.util.HashMap;
import java.util.Map;

public class AliasResolver {

    public AliasResolver() {
        this.aliases = new HashMap<String, String>();
    }

    public void addAliasMapping(Map<String, String> newAliases) {
        aliases.putAll(newAliases);
    }

    public boolean match(String pattern, String name) {
        return name.equals(pattern) ||
                (aliases.containsKey(name) && aliases.get(name).equals(pattern));
    }

    private Map<String, String> aliases;
}
