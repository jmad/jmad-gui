
.. highlight:: java

JMad Examples
=============

Twiss
-----

Running a simple twiss command on a model can be achieved by::

	model.init();

    List<Element> elements = model.getActiveRange().getElements();

    /* or perform some custom twiss */
    TfsResultRequestImpl request = new TfsResultRequestImpl();
    
    /* a regexp for the elements we want to retrieve */
    request.addElementFilter("BPM.*");
    
    /* and the variables, which we want to see */
    request.addVariable(MadxTwissVariable.NAME);
    request.addVariable(MadxTwissVariable.X);
    request.addVariable(MadxTwissVariable.Y);

    @SuppressWarnings("unused")
    TfsResult result = model.twiss(request);

    model.cleanup();
   