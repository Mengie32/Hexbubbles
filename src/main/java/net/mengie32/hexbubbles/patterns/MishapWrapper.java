package net.mengie32.hexbubbles.patterns;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation;
import net.minecraft.util.math.Vec3d;

/* This is some absolute jank that chatGPT helped me cook up. This needs to exist because:
 1. Kotlin does not have the concept of checked exceptions ⇒ Exceptions in Kotlin don't need to be explicitly handled or declared.
 2. fernflower decompiles hexcasting's Kotlin bytecode into java code, maintaining the "Throwable" superclass for Mishaps
 3. In Java, "Throwable" is a checked exception
 4. The compiler incorrectly believes that Mishaps must be explicitly handled or declared.
 5. I couldn't figure out how to get VScode to call Kotlin code from a Java file
 * 
 * I am throwing the mishap in an enviroment with the warning suppressed so that it can be propogated up to hexcasting without the compiler throwing a fit about an unhandled exception
 */

public class MishapWrapper {
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwMishap(Throwable t) throws T {
        throw (T) t;
    }

    // Wrapper for the assertVecInRange function that doesn't cause an unhandled exception error
    public static void assertVecInRange(CastingEnvironment env, Vec3d vec){
        try{
            env.assertVecInRange(vec);
        }catch(MishapBadLocation mishap){
            throwMishap(mishap);
        }
    }
}
