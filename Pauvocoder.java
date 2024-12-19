import java.util.Arrays;

import static java.lang.System.exit;

public class Pauvocoder {

    // Processing SEQUENCE size (100 msec with 44100Hz samplerate)
    final static int SEQUENCE = StdAudio.SAMPLE_RATE/10;

    // Overlapping size (20 msec)
    final static int OVERLAP = SEQUENCE/5 ;
    // Best OVERLAP offset seeking window (15 msec)
    final static int SEEK_WINDOW = 3*OVERLAP/4;

    public static void main(String[] args) {
        if (args.length < 2)
        {
            System.out.println("usage: pauvocoder <input.wav> <freqScale>\n");
            exit(1);
        }


        String wavInFile = args[0];
        double freqScale = Double.valueOf(args[1]);
        String outPutFile= wavInFile.split("\\.")[0] + "_" + freqScale +"_";

        // Open input .wev file
        double[] inputWav = StdAudio.read(wavInFile);
        StdAudio.save(outPutFile+"linformatique.wav", resample(inputWav, freqScale));

        // Resample test
        double[] newPitchWav = resample(inputWav, freqScale);

        // Simple dilatation
        double[] outputWav   = vocodeSimple(newPitchWav, 1.0/freqScale);
        StdAudio.save(outPutFile+"Simple.wav", outputWav);

        // Simple dilatation with overlaping
        outputWav = vocodeSimpleOver(newPitchWav, 1.0/freqScale);
        StdAudio.save(outPutFile+"SimpleOver.wav", outputWav);

        // Simple dilatation with overlaping and maximum cross correlation search
        outputWav = vocodeSimpleOverCross(newPitchWav, 1.0/freqScale);
        StdAudio.save(outPutFile+"SimpleOverCross.wav", outputWav);

        joue(outputWav);

        // Some echo above all
        outputWav = echo(outputWav, 100, 0.7);
        StdAudio.save(outPutFile+"SimpleOverCrossEcho.wav", outputWav);

        // Display waveform
        displayWaveform(outputWav);

    }

    /**
     * Resample inputWav with freqScale
     * @param inputWav
     * @param freqScale
     * @return resampled wav
     */
    public static double[] resample(double[] inputWav, double freqScale) {
        // If freqscale is equal to 0, we show the error's code.
        if (freqScale <= 0) {
            throw new IllegalArgumentException("freqScale can't be negative or equal to 0");
        }

        // If freqscale is equal to 1, no change and we return the table in parameter.
        if (freqScale==1){
            return inputWav;
        }
        // For get the length of new table, it have devide the length of the table in parameter with freqscale.

        int tailleNewWav = (int) (inputWav.length / freqScale);
        double[] newWav = new double[tailleNewWav];

        if(freqScale==1){
            return inputWav;
        }
        if(freqScale>1 || freqScale<1){
            int indiceInit;
            for(int newIndice=0; newIndice<tailleNewWav; newIndice++){
                indiceInit = (int)(newIndice*freqScale);
                newWav[newIndice]=inputWav[indiceInit];
            }
        }
        return newWav;

    }

    /**
     * Simple dilatation, without any overlapping
     * @param inputWav
     * @param dilatation factor
     * @return dilated wav
     */
    public static double[] vocodeSimple(double[] inputWav, double dilatation) {
        int count = 0;

        dilatation = 1 / dilatation;

        int newlength = (int)(inputWav.length * dilatation);

        double[] newinputWav = new double[newlength];

        int[] pass = new int[newlength/OVERLAP];

        int passi = 1 ;

        if (dilatation <= 0)
            throw new UnsupportedOperationException("Dilatation can't be negative or equal to 0");
        if (dilatation > 1 ){

            for(int i = 1; i < inputWav.length; i++){
                newinputWav[count] = inputWav[i];
                count++ ;

                if (i % OVERLAP == 0 && i != pass[passi-1]){
                    pass[passi] = i ;
                    passi++ ;
                    i -= OVERLAP - SEEK_WINDOW;
                    }
            }
        }
        else if (dilatation < 1 ){

            for(int i = 0; i < inputWav.length; i++){
                newinputWav[count] = inputWav[i];
                count++ ;
                if (i % SEEK_WINDOW == 0){
                i += OVERLAP - SEEK_WINDOW ;
                }
            }
        }
        else{
            return inputWav;
        }
        return newinputWav;
    }

    /**
     * Simple dilatation, with overlapping
     * @param inputWav
     * @param dilatation factor
     * @return dilated wav
     */
    public static double[] vocodeSimpleOver(double[] inputWav, double dilatation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Simple dilatation, with overlapping and maximum cross correlation search
     * @param inputWav
     * @param dilatation factor
     * @return dilated wav
     */
    public static double[] vocodeSimpleOverCross(double[] inputWav, double dilatation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Play the wav
     * @param wav
     */
    public static void joue(double[] wav) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Add an echo to the wav
     * @param wav
     * @param delay in msec
     * @param attn
     * @return wav with echo
     */
    public static double[] echo(double[] wav, double delay, double attn) {
        if (attn < 0 || attn > 1)
            throw new UnsupportedOperationException("Attn must be between 0 and 1");
        if (delay < 0)
            throw new UnsupportedOperationException("Delay must be non-negative");

        //Calculate the number of samples that delay represents
        int delaySamples = (int)(delay * StdAudio.SAMPLE_RATE / 1000);

        double[] sampleOutput = new double[wav.length + delaySamples];

        // Add the sample with delay and attn
        for (int i = 0; i < wav.length; i++) {
            sampleOutput[i + delaySamples] = attn * wav[i];
        }

        // Adds basic sound to echo
        for (int i = 0; i < wav.length; i++) {
            sampleOutput[i] += wav[i];

            if (sampleOutput[i] > 1.0)
                sampleOutput[i] = 1.0;
            if (sampleOutput[i] < -1.0)
                sampleOutput[i] = -1.0;
        }

        return sampleOutput;
    }

    /**
     * Display the waveform
     * @param wav
     */
    public static void displayWaveform(double[] wav) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-1, 1);
        double intervalle = (2.0 / (wav.length/4.0)) ;
        double position = -1 ;
        for (int i = 0 ; i < wav.length ; i+=4) {
            StdDraw.line(position, (-1*wav[i]/2.0), position, (wav[i]/2.0));
            position += intervalle;
        }
        StdDraw.show();
    }


}
