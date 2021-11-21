package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    /*
     * private static final int MIN = 0; private static final int MAX = 100; private
     * static final int ATTEMPTS = 10;
     */
    public static final int DMIN = 1;
    public static final int DMAX = 100;
    public static final int DNCHANCE = 10;
    private final DrawNumber model;
    private final List<DrawNumberView> views;
    private final Configuration confg = new Configuration(DMIN, DMAX, DNCHANCE);

    /**
     * @param configFile
     *                       the configuration file path
     * @param views
     *                       the views to attach
     */
    public DrawNumberApp(final String configFile, final DrawNumberView... views) {
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view : views) {
            view.setObserver(this);
            view.start();
        }
        try (var buff = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)))) {
            String line;
            while ((line = buff.readLine()) != null) {
                final StringTokenizer tok = new StringTokenizer(line, ":");
                while (tok.hasMoreTokens()) {
                    final String data = tok.nextToken();
                    if (data.contains("min")) {
                        this.confg.setMin(Integer.parseInt(tok.nextToken().trim()));
                    }
                    if (data.contains("max")) {
                        this.confg.setMax(Integer.parseInt(tok.nextToken().trim()));
                    }
                    if (data.contains("att")) {
                        this.confg.setChance(Integer.parseInt(tok.nextToken().trim()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("file not found " + e);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("I/O exception" + e1);
        }
        if (this.confg != null) {
            this.model = new DrawNumberImpl(this.confg.getMin(), this.confg.getMax(), this.confg.getChance());
        } else {
            displayError("read failed");
            this.model = new DrawNumberImpl(this.confg.getMin(), this.confg.getMax(), this.confg.getChance());
        }
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view : this.views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view : this.views) {
                view.numberIncorrect();
            }
        } catch (AttemptsLimitReachedException e) {
            for (final DrawNumberView view : this.views) {
                view.limitsReached();
            }
        }
    }

    private void displayError(final String err) {
        for (final DrawNumberView view : this.views) {
            view.displayError(err);
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    @SuppressWarnings("PMD.DoNotTerminateVM")
    public void quit() {
        System.exit(0);
    }

    /**
     * @param args
     *                 ignored
     * @throws FileNotFoundException
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp("res/config.yml", // res is part of the classpath!
                new DrawNumberViewImpl(), new DrawNumberViewImpl(), new PrintStreamView(System.out),
                new PrintStreamView("output.log"));
    }

}
