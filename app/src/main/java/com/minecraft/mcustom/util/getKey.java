package com.minecraft.mcustom.util;
// by Mono163(yyds)
import com.minecraft.mcustom.util.http.OKHttpUtil;
import com.minecraft.mcustom.util.http.HttpUrl;

import java.security.PrivateKey;

public class getKey {

    public static PrivateKey getPublicKey() throws Exception {
        return RSAUtils.getPrivateKey("-----BEGIN PRIVATE KEY-----\n" +
                "MIIJQwIBADANBgkqhkiG9w0BAQEFAASCCS0wggkpAgEAAoICAQDM6V3hWCCicYdV\n" +
                "k1S5CU020xGK5mSasUORU2EX3Jcba7F5Ho4LDKFXxcbdSGPsGHQ8Nat1cvrym/8c\n" +
                "oaVUBZpDxOhkU1RnNn9K4s9dtPdOvvHhxeWPcE05yOO3BwUJBtI0wOMw7ubk3vTZ\n" +
                "cwBDwyx7g83mIBJAMM/I3BLDiQTTaASwajYVyT/I5tXfSvoJul3MNFc1NWiMntPo\n" +
                "b+0yB5mYfyMA1H47OPqnk7oIkd/aeb4bdOnXo4gtzZGPxt+bo0pPtO5d1wRpGQfO\n" +
                "yKyCPLlZjs7fM1PQ/CdfXKhvrqGB3K1ZpmbZ/QYac2okuB5cMZc3amd6uQ/XRXYQ\n" +
                "5VUP4aMa5aDSZKR8k5IK2WkIQ3cyO6UYbQeqQLu6NVBWT2ds5SptYdRN7PC7WF7U\n" +
                "xmeCIbNkJ6b549JlZU1Xkl49YkrBsU5xlstK4bhyJZborZT9h0AqAQGmHY8cHeCt\n" +
                "7XBlxqaHSG/Gr4Ew4aHVdmphEagEQvez220F4LC9v1ab3kySxv/dC+StoKrsQxlH\n" +
                "hiCu6EOxwkhIPAi9+KkvdSrtxfelm+AkD9Fza8kaH2ecX/yk/xy2Oti9NmV7I7kP\n" +
                "IbgzrceyCCcRkvWDaw4jDS8KOWn9I1UPs7pF2cskygBQSFC4VFYfaJ9E2wlffqKA\n" +
                "DSSkuz6LTBRmvL5t+gAkYf8iiOKs2QIDAQABAoICAEboAhp2OmS70E7meozFETkF\n" +
                "dK6R5wI4kN768UAmjwtoCrOUxSZBqpsKzzPHVvxdwesLz1cHAJtvK6omrWUTwzvs\n" +
                "KVZ+F1jX3Nh2/7a3ZlSTcnPj3PnzrGVwhIvTHATxnU+v524JXVCZbjTIvLC2GvBq\n" +
                "vZcCRg58VYp1zPBou4jEF8tMFYcYasAEUMHB2h//o3GEMyEwaAsw8rcd9AA57QZ+\n" +
                "CAjDfyLTJrlSPy+z+rhJJGWv4TNDXV4sIlCUStA6gv5t54YFX3tWtAL0Ie23QZQh\n" +
                "/KfzxO8uMhmnoIl07mJXkIzTLggErFX+1h36sFbKuj7szP4Eh9zAJojWMscaFywa\n" +
                "obwt+JKbjAyfCWKUx/f+hPH5lRPFFq9LVymKS5ufVjrjBt+suEdi13R8S9C0sTjA\n" +
                "UdHAqMGqJv4u6d6OYzU2B3Bgx+SMRkaHZRq3swwM9dQRqH0wV5TnwdAbn4y+KcCb\n" +
                "Y3h8v1MAMfUpPPT1OcrBsGul+JHVaMkmiIrWw8fLrigKz5c3G9VQF0BFJZFxSpCM\n" +
                "eOX4uwgzfV4a6GxgR56VAeOWXA9k/p4FwTlCmE+E6DsXn648MpHgOn1xpchb9jga\n" +
                "jTVfOz0wbklW4BapnhKORS3JFdpnbtOUNsPqRgJLmBfA7FyoMed8n3wZFgBe5zr5\n" +
                "yvspLIZJS44ip+A9280BAoIBAQD1dJCjDOTtwDmj/0QWIQlRorBcsdgM2P604qJ1\n" +
                "UFY+AOclpfSbd5rpojCf5SsjvIUbsCawIQzKKXMVCuCqAuD90uXLrWZX1S6cvvNa\n" +
                "jnGbOUqtCI9LieUVcrR9CzPtTTnBtjZDXJO2/gbgBotAeyiik1UpfZlGrijSAOPY\n" +
                "97llVJyhdf5KBS6AMAzIii3Y5Oe1nDBmlsNJwiXxvDRAH+h/xqbXdi4fJaMyGNpI\n" +
                "C5WwulibkM0JDYhJaE1ZQPQSo9cDc+uBoT2ks4IxM2NNliTdhHk9MYOR/hKt1ldt\n" +
                "1gJE9/TWr+vkzSFJ+IQt86cDK9iDVOepD/Ux7Ri5SquKfw6pAoIBAQDVtupRq3Qc\n" +
                "Om/YNycynQUljok5MAjcDmRU8K8d68OmQsNKogw+weQYJREzK+GhoB1GI3V1AIra\n" +
                "H1nM6KWeLzpuNY2NTEvCL3Ox5IKC778mJCpm9DP1NYnDJ8i/NxxtPq0JVOUbKWFp\n" +
                "Ks1Dtj0mU7aJ2NRLQLncMt4Vhng0IF++Nl+4FCDkglX522K+EFhjpmHvfIqBmw8K\n" +
                "rsHlbt5FKOpqJIuNvxvWEHRfIGVwlWVmonv3naoZgwwVthYEH3JW3GBNz8y5yp29\n" +
                "A1k1pHzXUcEmactV0wUvHE3tW6mYvHLl6YTA5MlGoCCC1kWOekgwrv/8XAL4O0SK\n" +
                "X5Z7deqixXqxAoIBAQCO8la1jnHYgGVRO/1UAeNidqPNKAmR2eHkSESim6nOLEpx\n" +
                "Y4fj7rD6NWmqBRWtl0HR0TGGy4ieR6ts6svSVyDe0a7gglii+FVQFei1qVVCOAhk\n" +
                "YyLItEMEzauNUzPnlFz5knthmY2xOZmZaFY6tD8DB1jGdA4K2qyNONV46zQ+bhtV\n" +
                "8NiqFQByyQj41XhD43OKEa8iX9TVpMriUGABpFOL7o4F1x6fJxU2vMCKyqxfovat\n" +
                "ipPOHOJtFeBOBYTfQimbKixSbyvuOlis/KdtbO6FT3woBikAof4Q3U5HeiYSMnGV\n" +
                "ghiYRFqJsMCR3l86epN6UL0leVSrwc/vQSxL2RkZAoIBAQCMcZQy73VyR4TQlg4Q\n" +
                "WNfVAoJzLnnkAyE0IKc0BoegbydfmHZrl4eJxnIzKpcwW8RhH1RE+4JELW4aFpkg\n" +
                "BvFjE5ZeUNFIX16sfPq3iU4YhpPG7JxofDJ4KWDnKBRqRlH4W+3jl6IINrSF1vEA\n" +
                "iureU7vPaE27w0Hm62N6/fttSNZGZhPnly9g7ydIt/0eH20ksLdkzL1py8ZoPB+d\n" +
                "W3IRJsr+76QfXNjc2rFO9x6YABdvdhEeitwaOHrnYrMFGfW/rNthDSHz4ItzkRDp\n" +
                "fzRrRgL196LkfrlHToF5ef4X0QoIqVEMNJao5J75LqraM2bONLlO38P/GyoNIER9\n" +
                "i0FBAoIBACivXCOI4BzymVAYMgNsrTUh0V7B0nMtoDdfAolZhW1E61PUE3tKHGtT\n" +
                "GobmZZmh6QSS8UbWQ7FpYFuBiYAo9EgtZifRKbgW2Fse4b3K33BPo4gKvBz4tJR/\n" +
                "bp+CEW8q/cChvFsT21hXyXEQn8LP80K5utMn9KqM0roOiGI19S4hiWrni1V/PM57\n" +
                "riUkLMOCOuSuCIhLzLZG1aoPciG2jDYNN/mbF5eZKl5sgfoYZwLzV6tklbQKrPgv\n" +
                "zcv8lXPctugjY9OhhwadxE87OZQas+jXuZ4Zr57yi6JH1pDZqh0gkPL/NstF/qDN\n" +
                "dYpM1Eovt+O/koaREk2IirFREorR+rg=\n" +
                "-----END PRIVATE KEY-----");
    }
}