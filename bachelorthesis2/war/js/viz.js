var keyboard = function(keyboard){
		
	function getMelodyArray(melodyIndex, keyboard) {	
		
		var ngram = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val();

		ngram = ngram.replace(/\s{2,}/g, ' ');
		ngram = ngram.replace(/\-{2,}/g, '-');
		ngram = ngram.replace(/^\s+|\s+$/g, '');
		var melodies = ngram.split(',');

		if (melodyIndex >= melodies.length) return;

		var mel_str = melodies[melodyIndex];
		var ints = mel_str.replace(/\s{2,}/g, ' ').replace(/^\s+|\s+$/g, '').split(' ');
		return ints;
	}

	var renderInput = function(keyboard) {

		$('#canvas_div').empty();
		var ngram = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val();
		if (!ngram && ngram != "") {
			return;
		}
		var ngram = ngram.replace(/\s{2,}/g, ' ')
		ngram = ngram.replace(/\-{2,}/g, '-');
		ngram = ngram.replace(/^\s+|\s+$/g, '');//trim
		melodies = ngram.split(',');

		for (var m = 0; m < melodies.length; m++) {
			var canvas;
			if (navigator.appName == 'Microsoft Internet Explorer') {
				canvas = $('#canvas_div');
			} else {
				canvas = $('<canvas height="120"/>');
				$('#canvas_div').append(canvas);
			}
			renderMel(melodies[m], canvas[0],keyboard);
		}

		
		
		if (navigator.appName == 'Microsoft Internet Explorer' || getMelodyArray(1,keyboard) != null) return;
		
		$('#canvas_div canvas').bind("click", scoreOnClick);
		var scuts = $('<div class="shortcuts"><span id="shortcuts-text">Keyboard shortcuts</span></div>');
		var	scutsContent = '<div id="shortcut-popup" class="popups-box" style="position:absolute; top: 190px; left: 673px; "><table><tbody><tr><td class="popups-tl popups-border"></td><td class="popups-t popups-border"></td><td class="popups-tr popups-border"></td></tr><tr><td class="popups-cl popups-border"></td><td class="popups-c"><div class="popups-container"><div class="popups-title"><div class="popups-close"><a href="javascript:;">Close</a></div><div class="shortcut-title">Keyboard shortcuts</div><div class="clear-block"></div></div>'+
		'<div class="popups-body"><span><kbd class="arrow">?</kbd> previous note</span><span><kbd class="arrow">?</kbd> next note</span><br><span><kbd class="arrow">?</kbd> raise pitch</span><span><kbd class="arrow">?</kbd> lower pitch</span><br>' + 
		'<span><kbd class="arrow">Shift</kbd><kbd class="arrow">?</kbd> shorter duration</span><br><span><kbd class="arrow">Shift</kbd><kbd class="arrow">?</kbd> longer duration</span><br>' + 
		'<span><kbd>space</kbd> copy current note</span><br><span><kbd>del</kbd> delete current note</span></div>'
		+'<div class="popups-buttons"></div><div class="popups-footer"></div></div></td><td class="popups-cr popups-border"></td></tr><tr><td class="popups-bl popups-border"></td>      <td class="popups-b popups-border"></td>      <td class="popups-br popups-border"></td>    </tr>  </tbody></table></div>';
		$(".popups-close").live('click', function() {
			$("#shortcut-popup").detach();
			$("#shortcuts-text").show();
			_gaq.push(['_trackEvent', "Site", "Hide keyboard shortcuts"]);
		});
	    scuts.bind('click', function() {
	    	$('#canvas_div').append($(scutsContent));
	    	$("#shortcuts-text").hide();
	    	_gaq.push(['_trackEvent', "Site", "Show keyboard shortcuts"]);
	    });
	    $('#canvas_div').append(scuts);
	}

	function switchNgramRepresentation(keyboard) {
		
		
		var ngramType =  $("#ngram" + keyboard + "Container select").val();
		var ngram = $("#Keyboard" + keyboard + "Container  .gwt-TextBox").val();
				 		
		
		
		if (!ngram && ngram != "") return;
		var ngram = ngram.replace(/\s{2,}/g, ' ');
		ngram = ngram.replace(/\-{2,}/g, '-');
		ngram = ngram.replace(/^\s+|\s+$/g, '');//trim
		melodies = ngram.split(',');

		var out = "";
		for (m in melodies) {
			melody = melodies[m];
			var ints = melody.replace(/\s{2,}/g, ' ').
			replace(/^\s+|\s+$/g, '').split(' ');
			var b = ints[0].split('_')[0];
			if (b < 20 && ints.length > 1) {
				b = 57;
				ints.unshift(57);
			}

			out += ints.join(" ") + ", ";
		}
		out = out.substring(0, out.length - 2);
		ngram.val(out);

		
	}


	var timer1, timer2;

	function onGwtReady(keyboard) {
		renderInput(keyboard);
		flashPianoInit();

		
		
	
		//setTimeout(addFBLikeButton, 1000);
		//setTimeout(loadDisqus, 8000);

	
			
		$("#ngram" + keyboard + "Container select").change(function() {
			switchNgramRepresentation(keyboard);
			renderInput(keyboard);
		});
		
		


		$("#showCommentsContainer").show();
		$("#showComments").click(function() {
			$("#commentsContainer").toggle();
			if ($("#commentsContainer").is(':visible')) {
				$("#showComments").html("Hide comments");
				_gaq.push(['_trackEvent', "Site", "Show comments"]);
			}
			else {
				$("#showComments").html("Show comments");
				_gaq.push(['_trackEvent', "Site", "Hide comments"]);
			}
		});

		var contd = $(b[i]);
		contd.keydown(function(e) {
			if (e.which == '32') {
				renderInput(keyboard);
			}
		});
		


		contd.live('keyup', function(e){
			var c = e.which;
			var ngramField = $(b[i]);

			if (c < 32 && c != 8) {
				return;
			}

			if (c != 8 && c != 32 && c != 37 && c != 39 && (c < '48' || c > '57')) {
				var v = ngramField.val();
				var last = v.charAt(v.length - 1);
				if (last.match(/[\-0-9 ,_)(wrdhq81632]/g) == null) {
					ngramField.val(v.substring(0, v.length - 1));
				}
				return;
			}

			if (c == 8 && playedMelody.length > 0) {
				var ns = ngramField.val();
				// this means we have deleted a symbol (e.g. "-2")
				if (ns.charAt(ns.length - 1) == " ") {
					playedMelody.pop();
					noteTimes.pop();
				}
				if (ns.length == 0) {
					playedMelody = [];
					noteTimes = [];
				}
				clearTimeout(timer1);
				timer1 = setTimeout("renderInput(keyboard);", 300);
				return;
			}

			clearTimeout(timer1);
			timer1 = setTimeout("renderInput(keyboard);", 300);
			clearTimeout(timer2);
			if (ngramField.val().length > 0 && (typeof something === "undefined")) {
				timer2 = setTimeout("submitQuery();", 2000);
			}
		})
		contd.blur(renderInput(keyboard));   
		
		
		}
	

	var clearSubmitTimeout = function () {
		clearTimeout(timer2);
	}

	var submitQuery = function () {
		$('#sendButtonContainer .sendButton').click();
		prevNotePiano = null;
	}

	var ctx;

	var renderMel = function(mel_str, canvas,keyboard) {

		if (typeof Vex == 'undefined') return;
		var divIdCoreForKeyboard = {"timeage":"TimeAge","timecountry":"TimeCountry","timepublications":"TimePublications","keypercent":"KeyPercent","countrieskey":"CountriesKey","timekey":"TimeKey","agekey":"AgeKey","ngramcomposer":"NgramComposer"};
		var ngram = $("#Keyboard" + divIdCoreForKeyboard[keyboard] + "Container .gwt-TextBox").val();
		var ngramType =  $("#ngram" + divIdCoreForKeyboard[keyboard] + "Container select").val();
		
		var ints = mel_str.replace(/\s{2,}/g, ' ').replace(/^\s+|\s+$/g, '').split(' ');
		var b = ints[0].split('_')[0].split('(')[0];
		var d = ints[0].split('_')[0].split('(')[1];
		
		if (b < 20 && ints.length > 1) {
			
			if (b == "" && d != "") {
			} else {
				ints.unshift(57);
			}
			b = 57;
		}


		music = new Vex.Flow.Music();
		notes = [];
		var totalDuration = 0;
		var totalDots = 0;
		var totalAccidentals = 0;

		
		// compute min and max pitches 
		var minPitch = 127;
		var maxPitch = 0;
		var abs_pos;
		for (var i = 0; i < ints.length; i++) {
			var chord_raw = "" + ints[i];
			var chord_params = chord_raw.split("(");
			var notes_raw = chord_params[0];
			var chord = notes_raw.split("_");
			for (var j = 0; j < chord.length; j++) {
				if (ints[i] == "") continue;
				var curr_note = chord[j];
				abs_pos = (i == 0 && j == 0 ? b : abs_pos - - curr_note);
				maxPitch = Math.max(maxPitch, abs_pos);
				minPitch = Math.min(minPitch, abs_pos);
			}
		}
		var clef;
		if (maxPitch < 53 && minPitch < 43) {
			clef = "bass";
		} else {
			clef = "treble";
		}
		
		for (var i = 0; i < ints.length + (mel_str.length > 0 ? 1 : 0); i++) {
			if (i >= ints.length) continue;
			var abs_pos;
			var keys = [];
			var accidentals = [];

			if (ints[i] == "") continue;

			var chord_raw = "" + ints[i];
			var chord_params = chord_raw.split("(");
			if (chord_params.length > 2) {
				continue;
			}

			var notes_raw = chord_params[0];
			var duration = "q";
			if (chord_params.length == 2) {
				if (chord_params.indexOf(')') == 0) {
					continue;
				}
				var duration_raw = chord_params[1];
				var duration_int = duration_raw.substring(0, duration_raw.indexOf(')'));
				for (var durInd in durations) {
					var d = durations[durInd];
					if (d == duration_int) {
						duration = durationsVex[durInd];
					}
				}
			}

			var chord = notes_raw.split("_");
			for (var j = 0; j < chord.length; j++) {
				var curr_note = chord[j];
				abs_pos = (i == 0 && j == 0 ? b : abs_pos - - curr_note);
				
				abs_pos_clef = abs_pos - - (clef == "bass" ? 21 : 0);

				var rel_pos = abs_pos % 12;
				var key = music.getCanonicalNoteName(rel_pos);
				if (key == undefined) continue;
				var parts = music.getNoteParts(key);
				var acc = parts['accidental'];
				accidentals[j] = acc;
				var octave = Math.floor(abs_pos / 12);
				keys.push(key + "/" + octave);
			}

			notes[i] = new Vex.Flow.StaveNote({ clef: clef, keys: keys, duration: duration });

			var durationBeats = notes[i].getTicks() / Vex.Flow.RESOLUTION * 4;

			totalDuration = totalDuration + durationBeats; 

			notes[i] = notes[i].setStemDirection(abs_pos - - (clef == "bass" ? 21 : 0) > 59 ? 
					Vex.Flow.StaveNote.STEM_DOWN : 
						Vex.Flow.StaveNote.STEM_UP);

			if (durationIsDotted(duration)) {
				notes[i].addDotToAll();
				totalDots++;
			}

			var gotAccidental = false;
			for (var j = 0; j < chord.length; j++) {
				var acc = accidentals[j];
				if (acc) {
					notes[i] = notes[i].addAccidental(j, new Vex.Flow.Accidental(acc));
					gotAccidental = true;
				}
			}
			if (gotAccidental) totalAccidentals++;
		}

		var width = 33 * (ints.length + 1 + (mel_str.length > 0 ? 1 : 0)) + 
		33 * (totalAccidentals*0 + 3) + 10 * totalDots;
		canvas.width = width + 30;

		var stave;
		if (navigator.appName == 'Microsoft Internet Explorer') {
			ctx = Vex.Flow.Renderer.getRaphaelContext(canvas, width + 20, 120);
			stave = new Vex.Flow.Stave(10, 0, width);
			stave.setContext(ctx);
		} else {
			var renderer = new Vex.Flow.Renderer(canvas,
					Vex.Flow.Renderer.Backends.CANVAS);
			ctx = renderer.getContext();
			stave = new Vex.Flow.Stave(10, 0, width);
		}
		stave.addClef(clef).setContext(ctx).draw();

		noteOffsetLeft = stave.start_x + stave.glyph_start_x;

		// Create a voice in 4/4
		var voice = new Vex.Flow.Voice({
			num_beats: totalDuration,
			beat_value: 4,
			resolution: Vex.Flow.RESOLUTION
		});

		// Add notes to voice
		voice.addTickables(notes);

		// Format and justify the notes
		formatter = new Vex.Flow.Formatter().joinVoices([voice]).format([voice], width - 30);

		// Render voice
		voice.draw(ctx, stave);
	}

	//flash piano interaction

	var playedMelody = [];
	var noteTimes = [];

	var playedNote = function(pitch,keyboard) {
		prevNotePiano = pitch;
		
		
		var ngramType =  $("#ngram" + keyboard + "Container select").val();
		var prevNav = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val();
				 		
		
		
		prevNgram = prevNgram.replace(/^\s+|\s+$/g, '');
		if (playedMelody.length > 0) {
			var prevNote = playedMelody[playedMelody.length - 1];
			diff = (0 - - pitch) - (0 - - prevNote);
			var newNgram = prevNgram + " " + diff;
			$("#Keyboard" + keyboard + "Container .gwt-TextBox").val(newNgram);
		} else /* if (prevNgram != "") {
			var newNgram = prevNgram + ", " + pitch;
			$("#contentContainer .gwt-TextBox").val(newNgram);
		} else if (prevNgram == "") */ {
			$("#Keyboard" + keyboard + "Container .gwt-TextBox").val(pitch);
		}
		renderInput(keyboard);
		playedMelody.push(pitch);
		noteTimes.push(new Date().getTime());
	} 

	var flashPianoInit = function() {

		$('#sendButtonContainer .sendButton').click(function() {
			if (noteTimes.length > 0) {
				var noteTimeDiffs = [noteTimes[0]];
				var pitchDiffs = [playedMelody[0]];
				for (var i = 1; i < noteTimes.length; i++) {
					noteTimeDiffs[i] = noteTimes[i] - noteTimes[i-1];
					pitchDiffs[i] = playedMelody[i] - playedMelody[i-1];
				}
				var s = '{"p":['+pitchDiffs.toString()+'],"t":['+noteTimeDiffs.toString()+']}';
				_gaq = _gaq || [];
				_gaq.push(['_trackEvent', "Search Parameters", "Piano input", s]);
			}

			clearTimeout(timer1);
			clearTimeout(timer2);

			prevNotePiano = null;
			playedMelody = [];
			noteTimes = [];
		});

	}

	function addFBLikeButton() {
		var fbHtml = '<a><iframe src="http://www.facebook.com/plugins/like.php?'+
		'href=http%3A%2F%2Fwww.peachnote.com&amp;send=false&amp;layout=button_count&amp;width=50&amp;show_faces=false&amp;action=like&amp;colorscheme=light&amp;font&amp;height=21" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:50px; height:21px;" allowTransparency="true"></iframe></a>';
		$("#fb-like-button").html(fbHtml);
	} 


	var disqus_shortname = 'peachnote';
	var disqus_url = 'http://www.peachnote.com';
	var disqus_identifier = "/"; 

	function loadDisqus() {   
		var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
		dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
		(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
	}

	function disqus_config() {
		this.callbacks.onNewComment = [function() {
			_gaq.push(['_trackEvent', "Site", "New comment"]);
		}];
	}

	//GetSatisfaction feedback
	$("#fdbk_tab").live('click', function(){
		_gaq.push(['_trackEvent', "Site", "Feedback button"]);
	});


	var tickIndex = 0;

	var canvasOffset;
	var noteOffsetLeft = 0;
	var cursorHeight = 150;
	var selectedNoteIndex;
	var isScoreEditMode = false;

	function scoreOnBlur(keyboard) {
		isScoreEditMode = false;
		$('[id!="canvas_div"]').unbind("focus", scoreOnBlur);
		$(window).unbind("keydown", onScoreKeyPress);
		selectedNoteIndex = null;
		renderInput(keyboard);
	}

	function onScoreKeyPress(e,keyboard) {

		if ((e.which < 37 || e.which > 40) && e.which != 46 && e.which != 32) return;

		if (e.which == 38) changeMelodyNote(selectedNoteIndex, 1,keyboard);
		if (e.which == 40) changeMelodyNote(selectedNoteIndex, -1,keyboard);
		if (e.which == 37 && !e.shiftKey && selectedNoteIndex > 0) changeSelectedNote(-1,keyboard);
		if (e.which == 39 && !e.shiftKey) changeSelectedNote(1,keyboard);
		if (e.which == 37 && e.shiftKey) changeSelectedNoteDuration(-1,keyboard);
		if (e.which == 39 && e.shiftKey) changeSelectedNoteDuration(1,keyboard);
		if (e.which == 46) removeNote(selectedNoteIndex,keyboard);
		if (e.which == 32) addNoteAfter(selectedNoteIndex,keyboard);

		renderInput(keyboard);
		highlightNote(selectedNoteIndex);

		return false;
	}

	function addNoteAfter(noteIndex,keyboard) {
		var ints = getMelodyArray(0,keyboard);
		var ngram = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val();
		var chord = "" + ints[noteIndex];
		var chordBase = chord;
		var duration = "";
		if (chord.indexOf("(") > 0 && chord.indexOf(")") > chord.indexOf("(")) {
			duration = chord.substring(chord.indexOf("(") + 1, chord.indexOf(")"));
			chordBase = chord.substring(0, chord.indexOf("("));
		} else {
			duration = chord.substring(chord.indexOf("(") + 1, chord.indexOf(")"));
			chordBase = "57";
		}
		
		var diff = -chordBase.split("_")[0];
		$.each(chordBase.split("_"), function() {
		    diff += 0 - - this;
		});
		
		var chordTail = chordBase.split("_");
		chordTail.splice(0, 1);
		var newBaseChord = -diff + (chordTail.length > 0 ? "_" + chordTail.join("_") : "")
		var newDuration = (duration != "" ? "(" + duration + ")": "");
		var newChord = newBaseChord + newDuration;
		
		ints.splice(noteIndex + 1, 0, newChord);
		selectedNoteIndex += 1;
		var new_mel_str = ints.join(" ");
		 $("#Keyboard" + keyboard + "Container .gwt-TextBox").val(new_mel_str);
		_gaq.push(['_trackEvent', "Search parameters", "Visual score editor: note copied"]);
	}

	function changeSelectedNote(amount,keyboard) {
		var ints = getMelodyArray(0,keyboard);
		if (ints.length > selectedNoteIndex + amount) {
			selectedNoteIndex = selectedNoteIndex + amount;
		}
		_gaq.push(['_trackEvent', "Search parameters", "Visual score editor: selected note changed"]);
	}

	var durationsVex = ["w", "hd", "h", "qd", "q", "8d", "8", "16d", "16", "32", "64"];
	var durations   = ["32", "24", "16", "12", "8", "6", "4", "3", "2", "1", "0"];

	function durationIsDotted(duration) {
		return $.inArray(duration, ["hd", "qd", "8d", "16d"]) != -1;
	}

	function changeDuration(duration, amount) {
		var out = duration;
		for (var i = 0; i < durations.length; i++) {
			if (durations[i] == duration && i + amount < durations.length && i + amount >= 0) {
				out = durations[i - - amount];
			}
		}
		return out;
	}

	function changeSelectedNoteDuration(amount,keyboard) {
		var ints = getMelodyArray(0,keyboard);
		var ngramType =  $("#ngram" + keyboard + "Container select").val();
		var ngram = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val();
				 		
		
		
		
		var chord = "" + ints[selectedNoteIndex];
		var duration = "8";
		var baseChord = chord; 

		if (chord.indexOf("(") >= 0 && chord.indexOf(")") > chord.indexOf("(")) {
			duration = chord.substring(chord.indexOf("(") + 1, chord.indexOf(")"));
			baseChord = chord.substring(0, chord.indexOf("("));
		} 
		
		var d2 = changeDuration(duration, amount)
		chord = baseChord + "(" + d2 + ")";
		if (d2 == "q") chord = baseChord; 
		ints[selectedNoteIndex] = chord;

		var new_mel_str = ints.join(" ");
		$("#Keyboard" + keyboard + "Container .gwt-TextBox").val(new_mel_str);
		_gaq.push(['_trackEvent', "Search parameters", "Visual score editor: duration changed"]);
	}

	function transposeChord(chord, amount) {
		var baseChord = chord;
		var duration = "";
		if (chord.indexOf("(") > 0 && chord.indexOf(")") > chord.indexOf("(")) {
			duration = chord.substring(chord.indexOf("(") + 1, chord.indexOf(")"));
			baseChord = chord.substring(0, chord.indexOf("("));
		} else {
			duration = chord.substring(chord.indexOf("(") + 1, chord.indexOf(")"));
		}
		
		if (baseChord.indexOf("_") != -1) {
			var chordNotes = baseChord.split("_");
			chordNotes[0] = chordNotes[0] - - amount;
			return chordNotes.join("_") + (duration != "" ? "(" + duration + ")" : "");
		} else {
			return baseChord - - amount + (duration != "" ? "(" + duration + ")" : "");
		}
	}

	function changeMelodyNote(noteIndex, amount,keyboard) {
		
		var ints = getMelodyArray(0,keyboard);
		var ngram = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val();
				 		
		ints[noteIndex] = transposeChord(ints[noteIndex], amount);

		if (ints.length > noteIndex + 1) {
			ints[noteIndex + 1] = transposeChord(ints[noteIndex + 1], -amount);
		} else {
			playedMelody[playedMelody.length - 1] -= -amount;
		}
		var new_mel_str = ints.join(" ");
		$("#Keyboard" + keyboard + "Container .gwt-TextBox").val(new_mel_str);
		_gaq.push(['_trackEvent', "Search parameters", "Visual score editor: note transposed"]);
	}

	function removeNote(noteIndex,keyboard) {
		var ints = getMelodyArray(0,keyboard);
		var ngramType =  $("#ngram" + keyboard + "Container select").val();
		var ngram = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val();
		
			
		if (ints.length <= 1) return;

		if (noteIndex == ints.length - 1) {
			selectedNoteIndex = selectedNoteIndex - 1;
			tickIndex -= notes[noteIndex].ticks;
		}
		
		var chord = ints.splice(noteIndex, 1) + "";
		var chordBase = chord.split("(")[0];
		var diff = 0;
		$.each(chordBase.split("_"), function() {
		    diff += 0 - - this;
		});

		if (noteIndex < ints.length) {
			if (noteIndex > 0) {
				ints[noteIndex] = transposeChord(ints[noteIndex], diff);
			} else {
				ints[0] = transposeChord(ints[0], diff);
			}
		}
		var new_mel_str = ints.join(" ");
		$("#Keyboard" + keyboard + "Container .gwt-TextBox").val(new_mel_str);
		_gaq.push(['_trackEvent', "Search parameters", "Visual score editor: note deleted"]);}


	function scoreOnClick(e,keyboard) {
		if (getMelodyArray(1,keyboard) != null) {
			return;
		}
		
		
		var ngramType =  $("#ngram" + keyboard + "Container select").val();
		var ngram = $("#Keyboard" + keyboard + "Container .gwt-TextBox").val(); 	
		
		_gaq.push(['_trackEvent', "Search parameters", "Visual score clicked"]);

		if (!isScoreEditMode) {
			_gaq.push(['_trackEvent', "Search parameters", "Visual score edit mode activated"]);
		}
		
		isScoreEditMode = true;

		$('[id!="canvas_div"]').bind("focus", scoreOnBlur);
		$(window).unbind("keydown", onScoreKeyPress);
		$(window).keydown(onScoreKeyPress);

		var canvas =  $("#canvas_div > canvas");

		canvasOffset = canvas.offset();
		// if notes exist enable canvas click event
		if (notes.length > 0) {
			// mouse event handler code from: http://diveintohtml5.org/canvas.html
			var x, y;
			if (e.pageX != undefined && e.pageY != undefined) {
				x = e.pageX;
				y = e.pageY;
			}
			else {
				x = e.clientX + document.body.scrollLeft +
				document.documentElement.scrollLeft;
				y = e.clientY + document.body.scrollTop +
				document.documentElement.scrollTop;
			}
			x -= canvasOffset.left;
			y -= canvasOffset.top;

			selectedNoteIndex = findNote(x);

			renderInput(keyboard);
			highlightNote(selectedNoteIndex);
		} else {
			var new_mel_str = "57";
			playedMelody.push(57);
			ngram(new_mel_str);
			selectedNoteIndex = 0;
			renderInput(keyboard);
			highlightNote(selectedNoteIndex);
		}
	}


	function findNote(xcord) {
		var canvas =  $("#canvas_div > canvas");

		if (formatter.tContexts.map[tickIndex] == undefined) {				
			tickIndex -= notes[notes.length-1].ticks;
		}
		var dif = canvas[0].width;
		// set tickIndex for note
		var noteIndex;
		var i = 0
		for (var note in formatter.tContexts.map){
			// skip bar notes in note array
			if (formatter.tContexts.map[note].maxTicks == 0) {
				continue;
			}
			var d2 = noteOffsetLeft + formatter.tContexts.map[note].x + formatter.tContexts.map[note].width - xcord;
			var da = Math.abs(d2);
			if (da < dif) {
				dif = da;
				tickIndex = note;
			}
			i++;
		}

		// if user clicks for a new note (anything to the right of the last existing note)
		/*
		if ((noteOffsetLeft + formatter.tContexts.map[tickIndex].x + formatter.tContexts.map[tickIndex].width + 30 - xcord) < 0) {
			tickIndex = 0;
			for (var i=0; i <= notes.length-1; i++) {
				tickIndex += notes[i].ticks;
			}
			noteIndex = notes.length;
		}
		 */
		// set noteIndex for 'notes' array based on tickIndex 'map' object
		var i = 0;
		for (var note in formatter.tContexts.map) {
			if (tickIndex == note) {
				noteIndex = i;
				break;
			}
			i++;
		}

		return noteIndex;
	}

	function highlightNote(noteIndex) {

		ctx.fillStyle = "rgba(200,0,0,0.4)";
		// if notes exist
		if (notes.length > 0) {
			// when adding a new note vs. editing an existing note draw the cursor for next new note 
			//(the tickIndex will be undefined in map object for a new note)
			if (formatter.tContexts.map[tickIndex] == undefined) {
				var tempIndex = notes[selectedNoteIndex].ticks;
				var t = formatter.tContexts.map[tempIndex];
				ctx.fillRect(noteOffsetLeft + t.x + 60, 10, 16.5, cursorHeight);
			}
			else {
				var t;
				var i = 0;
				for (var note in formatter.tContexts.map) {
					if (i == noteIndex) {
						t = formatter.tContexts.map[note];
						tickIndex = note;
						break;
					}
					i++;
				}
				ctx.fillRect(noteOffsetLeft + t.x - 10, 10, t.width*0 + 22 + t.padding*2, cursorHeight);
			}
		}
		else {
			ctx.fillRect(noteOffsetLeft, 10, 16, cursorHeight);
		}
		ctx.fillStyle = "#000";
	}

	
}

var keyboardTimeAge = new keyboard("TimeAge");
var keyboardTimeCountry = new keyboard("TimeCountry");
var keyboardTimePublications = new keyboard("TimePublications");
var keyboardKeyPercent = new keyboard("KeyPercent");
var keyboardCountriesKey = new keyboard("CountriesKey");
var keyboardTimeKey = new keyboard("TimeKey");
var keyboardAgeKey = new keyboard("AgeKey");
var keyboardNgramComposer = new keyboard("NgramComposer");
