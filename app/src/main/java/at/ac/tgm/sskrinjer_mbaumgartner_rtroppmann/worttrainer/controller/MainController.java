package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.TimeListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.EinstellungenListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.SpieleListener;

public interface MainController extends TimeListener, SpieleListener, EinstellungenListener, SpielListener {}