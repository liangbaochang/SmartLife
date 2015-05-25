package com.laughingFace.microWash.deviceControler.model.infc;

import com.laughingFace.microWash.deviceControler.model.Model;
import com.laughingFace.microWash.deviceControler.model.Progress;

public interface ModelStateListener {

	public abstract void onStart(Model model);

	public abstract void onProcessing(Progress progress);

	public abstract void onFinish();

	public abstract void onInterupt();

	public abstract void faillOnStart();

}
