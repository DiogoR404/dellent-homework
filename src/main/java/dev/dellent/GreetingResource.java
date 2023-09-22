package dev.dellent;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.math.BigInteger;
import java.util.HashMap;

@Path("/")
public class GreetingResource {

    private HashMap<Integer, BigInteger> computationCache = new HashMap<Integer, BigInteger>();
    private int maxCached = 3;

    public GreetingResource() {
        computationCache.put(0, BigInteger.valueOf(0));
        computationCache.put(1, BigInteger.valueOf(1));
        computationCache.put(2, BigInteger.valueOf(0));
        computationCache.put(3, BigInteger.valueOf(1));

    }

    @GET
    @Path("labseq/{n}")
    @Produces(MediaType.APPLICATION_JSON)
    public String labseq(int n) {
        if (n < 0) {
            return "ERROR: Must be a positive number!";
        }

        return "{\"res\": \""+String.valueOf(getResult(n))+ "\"}";
    }

    public BigInteger getResult(int n) {
        if (computationCache.containsKey(n)) {
            return computationCache.get(n);
        }

        for (maxCached++; maxCached <= n; maxCached++) {
            computationCache.put(maxCached, computationCache.get(maxCached - 4).add(computationCache.get(maxCached - 3)));
        }
        maxCached--;

        return computationCache.get(n);
    }

}
