/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.domain.misalign;

/**
 * This class gives an access to multiple misalignment of the provided class/pattern with provided gauss distribution
 * for DX and DY ONLY!
 * 
 * @author agorzaws
 */
public class PatternOrClassMisalignmentConfiguration extends MisalignmentConfiguration {

    private final String classToMisalign;
    private final double gaussianDistribution;
    private final int seed;

    /**
     * Default constructor that provides 2.5sigma for gaussian distribution
     * 
     * @param pattern
     * @param classToMisalign
     * @param seed
     */
    public PatternOrClassMisalignmentConfiguration(String pattern, String classToMisalign, int seed) {
        super(pattern);
        this.classToMisalign = classToMisalign;
        this.gaussianDistribution = 2.5;
        this.seed = seed;
    }

    /**
     * @param pattern
     * @param classToMisalign
     * @param seed
     * @param gaussianDistribution
     */
    public PatternOrClassMisalignmentConfiguration(String pattern, String classToMisalign, int seed,
            double gaussianDistribution) {
        super(pattern);
        this.classToMisalign = classToMisalign;
        this.gaussianDistribution = gaussianDistribution;
        this.seed = seed;
    }

    public String getElementClass() {
        return classToMisalign;
    }

    public double getGaussianDistribution() {
        return gaussianDistribution;
    }

    public int getSeed() {
        return seed;
    }

    @Override
    public String toString() {
        return "[classToMisalign=" + classToMisalign + ", gaussianDistribution=" + gaussianDistribution + ", seed="
                + seed + ", getElementName()=" + getElementName() + "]";
    }

}
